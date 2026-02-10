package com.medichat.domain

import com.medichat.BuildConfig
import com.medichat.data.local.MedicalHistoryDao
import com.medichat.data.local.MedicalHistoryEntity
import com.medichat.data.remote.ChatCompletionRequest
import com.medichat.data.remote.ChatCompletionsApi
import com.medichat.data.remote.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class MedicalAssistantRepository(
    private val api: ChatCompletionsApi,
    private val historyDao: MedicalHistoryDao
) {
    private val parser = Json { ignoreUnknownKeys = true }

    fun observeHistory(): Flow<List<MedicalRecord>> {
        return historyDao.observeAll().map { records ->
            records.map {
                MedicalRecord(
                    id = it.id,
                    symptoms = it.symptoms,
                    diagnosis = it.diagnosis,
                    treatment = it.treatment,
                    recommendation = it.recommendation,
                    createdAt = it.createdAt
                )
            }
        }
    }

    suspend fun diagnose(symptoms: String): DiagnosisResult {
        val response = api.createChatCompletion(
            ChatCompletionRequest(
                model = BuildConfig.LLM_MODEL_NAME,
                messages = listOf(
                    ChatMessage(
                        role = "system",
                        content = """
                            You are a careful medical triage assistant. Do not claim certainty.
                            Return strict JSON with fields: diagnosis, treatment, recommendation.
                            Mention when emergency care is required.
                        """.trimIndent()
                    ),
                    ChatMessage(role = "user", content = symptoms)
                )
            )
        )

        val content = response.choices.firstOrNull()?.message?.content.orEmpty()
        val parsed = runCatching { parser.decodeFromString<DiagnosisPayload>(content) }.getOrNull()

        val result = DiagnosisResult(
            diagnosis = parsed?.diagnosis ?: "Could not parse diagnosis. Raw output: $content",
            treatment = parsed?.treatment ?: "No treatment advice parsed.",
            recommendation = parsed?.recommendation ?: "Consult a healthcare professional."
        )

        historyDao.insert(
            MedicalHistoryEntity(
                symptoms = symptoms,
                diagnosis = result.diagnosis,
                treatment = result.treatment,
                recommendation = result.recommendation,
                createdAt = System.currentTimeMillis()
            )
        )

        return result
    }
}

data class DiagnosisResult(
    val diagnosis: String,
    val treatment: String,
    val recommendation: String
)

data class MedicalRecord(
    val id: Long,
    val symptoms: String,
    val diagnosis: String,
    val treatment: String,
    val recommendation: String,
    val createdAt: Long
)

@Serializable
private data class DiagnosisPayload(
    val diagnosis: String,
    val treatment: String,
    val recommendation: String
)
