package com.challenge.networking

import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyParameterInterceptor(
    private val apiKey: String,
) : Interceptor {

    companion object {
        private const val PARAM_API_KEY = "key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(PARAM_API_KEY, apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}