package com.example.data.network

import com.example.data.local.TokenLifetimeStore
import jakarta.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoffeePointsApiFactory @Inject constructor(
    tokenLifetimeStore: TokenLifetimeStore,
) {

    private val token = tokenLifetimeStore.getToken()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val originalRequest = chain.request()

            val newRequest = originalRequest
                .newBuilder()
                .addHeader(AUTHORIZATION, "$BEARER $token")
                .build()

            chain.proceed(newRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val coffeePointsApiService = retrofit.create(CoffeePointsApiService::class.java)

    companion object {
        private const val BASE_URL = "http://212.41.30.90:35005/"
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }

}