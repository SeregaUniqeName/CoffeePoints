package com.example.data.local

import android.content.SharedPreferences
import jakarta.inject.Inject
import androidx.core.content.edit

class TokenLifetimeStoreImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : TokenLifetimeStore {

    override fun save(encryptedToken: String, currentTime: Long, lifetime: Long) {
        sharedPreferences.edit {
            putStringSet(
                CURRENT_USER,
                mutableSetOf(encryptedToken, currentTime.toString(), lifetime.toString())
            )
        }
    }

    override fun get() : Set<String> {
        return sharedPreferences.getStringSet(CURRENT_USER, mutableSetOf("", "0", "0"))!!
    }

    companion object {
        private const val CURRENT_USER = "currentUser"
    }
}