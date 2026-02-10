package com.medichat.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = System.getenv("LLM_API_KEY").orEmpty()

        val request = chain.request().newBuilder().apply {
            if (apiKey.isNotBlank()) {
                addHeader("Authorization", "Bearer $apiKey")
            }
            addHeader("Content-Type", "application/json")
        }.build()

        return chain.proceed(request)
    }
}
