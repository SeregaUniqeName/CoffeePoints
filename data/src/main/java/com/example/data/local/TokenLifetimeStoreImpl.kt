package com.example.data.local

import android.content.SharedPreferences
import jakarta.inject.Inject
import androidx.core.content.edit

class TokenLifetimeStoreImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : TokenLifetimeStore {

    override fun save(encryptedToken: String) {
        sharedPreferences.edit {
            putString(
                TOKEN,
                encryptedToken
            )
        }
    }

    override fun getToken(): String {
        val result = sharedPreferences.getString(TOKEN, "")
        if (result.isNullOrBlank()) throw RuntimeException("Token doesn't exist!!")
        else return result
    }

    companion object {
        private const val TOKEN = "token"
    }
}