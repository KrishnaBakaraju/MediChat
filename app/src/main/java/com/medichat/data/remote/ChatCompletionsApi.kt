package com.medichat.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatCompletionsApi {
    @POST("v1/chat/completions")
    suspend fun createChatCompletion(@Body request: ChatCompletionRequest): ChatCompletionResponse
}

@Serializable
data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Double = 0.2
)

@Serializable
data class ChatMessage(
    val role: String,
    val content: String
)

@Serializable
data class ChatCompletionResponse(
    val choices: List<ChatChoice> = emptyList()
)

@Serializable
data class ChatChoice(
    val message: ChatMessage = ChatMessage(role = "assistant", content = ""),
    @SerialName("finish_reason") val finishReason: String? = null
)
