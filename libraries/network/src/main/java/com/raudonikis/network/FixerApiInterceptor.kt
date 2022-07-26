package com.raudonikis.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

internal class FixerApiInterceptor @Inject constructor() : Interceptor {

    /**
     * Here you can define every header to be added in API requests
     */
    private val headers: Map<String, String>
        get() = mapOf("apikey" to FIXER_API_KEY)

    override fun intercept(chain: Interceptor.Chain): Response {
        with(addHeaders(chain.request())) {
            return chain.proceed(
                newBuilder()
                    .url(url)
                    .build()
            )
        }
    }

    /**
     * Adds headers defined in [headers] to the request
     */
    private fun addHeaders(request: Request): Request {
        return request
            .newBuilder()
            .apply {
                headers.map { header ->
                    addHeader(header.key, header.value)
                }
            }
            .build()
    }
}
