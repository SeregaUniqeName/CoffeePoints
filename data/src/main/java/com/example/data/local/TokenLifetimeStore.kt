package com.example.data.local

interface TokenLifetimeStore {

    fun save(encryptedToken: String, currentTime: Long, lifetime: Long)
    fun get() : Set<String>
}