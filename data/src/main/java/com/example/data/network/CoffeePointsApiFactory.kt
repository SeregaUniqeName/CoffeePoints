package com.example.data.network

import com.example.data.TOKEN_ALIAS
import com.example.data.TokenOutOfTimeException
import com.example.data.local.EncryptedStore
import com.example.data.local.TokenLifetimeStore
import jakarta.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoffeePointsApiFactory @Inject constructor(
    tokenLifetimeStore: TokenLifetimeStore,
    private val encryptedStore: EncryptedStore,
) {

    private val encryptedToken: String

    init {
        val tokenData = tokenLifetimeStore.get()
        val list = tokenData.toList()
        val start = list[1].toLong()
        val lifetime = list[2].toLong()
        val current = System.currentTimeMillis()
        if (current - start < lifetime) {
            encryptedToken = list[0]
        } else {
            throw TokenOutOfTimeException()
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newUrl = originalRequest
                .url
                .newBuilder()
                .addQueryParameter(TOKEN,
                    encryptedStore.decrypt(encryptedToken, TOKEN_ALIAS)
                )
                .build()

            val newRequest = originalRequest
                .newBuilder()
                .url(newUrl)
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
        private const val TOKEN = "token"
    }

}