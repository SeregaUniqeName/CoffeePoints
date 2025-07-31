package com.example.data.network.models

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthApiFactory {

    private const val BASE_URL = "http://212.41.30.90:35005/"
    private const val HEADER_NAME = "Content-Type"
    private const val HEADER_VALUE = "application/json"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(name = HEADER_NAME, value = HEADER_VALUE)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val authApiService = retrofit.create(AuthApiService::class.java)
}