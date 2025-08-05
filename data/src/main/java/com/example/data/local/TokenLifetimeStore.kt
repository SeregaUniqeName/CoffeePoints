package com.example.data.local

interface TokenLifetimeStore {

    fun save(encryptedToken: String)
    fun getToken() : String
}