package com.faza.quippertest.services

import com.faza.quippertest.App
import com.faza.quippertest.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkBuilder {

    private const val CONNECT_TIMEOUT = 60L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 15L

    private val headerInterceptor = HeaderInterceptor.Builder()
        .addHeader("X-RapidAPI-Key", BuildConfig.apiKey)
        .build()

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(headerInterceptor)
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            addInterceptor(
                ChuckerInterceptor.Builder(App.getContext())
                    .collector(ChuckerCollector(App.getContext()))
                    .redactHeaders(listOf())
                    .alwaysReadResponseBody(false)
                    .build()
            )
        }
        addInterceptor(headerInterceptor)
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    }.build()

    val apiService: ApiServices = Retrofit.Builder()
        .baseUrl(BuildConfig.baseUrl)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiServices::class.java)
}