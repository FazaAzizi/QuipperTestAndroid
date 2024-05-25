package com.faza.quippertest.services

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor internal constructor(
    private val headers: Headers,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = request.newBuilder()
            .headers(headers)
            .build()
        return chain.proceed(request)
    }

    class Builder {
        private var header: Headers.Builder = Headers.Builder()

        fun addHeader(head: String, value: String): Builder = apply {
            header = header.add(head, value)
        }

        fun build(): HeaderInterceptor = HeaderInterceptor(header.build())
    }
}