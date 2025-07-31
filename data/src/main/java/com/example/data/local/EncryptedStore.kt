package com.example.data.local

interface EncryptedStore {

    fun createKey(alias: String)
    fun encrypt(input: String, alias: String) : String
    fun decrypt(input: String, alias: String) : String
}