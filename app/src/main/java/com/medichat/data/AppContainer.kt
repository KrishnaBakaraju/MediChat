package com.medichat.data

import android.content.Context
import com.medichat.BuildConfig
import com.medichat.data.local.MedicalHistoryDatabase
import com.medichat.data.remote.AuthInterceptor
import com.medichat.data.remote.ChatCompletionsApi
import com.medichat.domain.MedicalAssistantRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(context: Context) {
    private val database = MedicalHistoryDatabase.create(context)

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.LLM_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val chatCompletionsApi = retrofit.create(ChatCompletionsApi::class.java)

    val medicalAssistantRepository = MedicalAssistantRepository(
        api = chatCompletionsApi,
        historyDao = database.medicalHistoryDao()
    )
}
