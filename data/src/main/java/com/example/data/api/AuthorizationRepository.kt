package com.example.data.api

interface AuthorizationRepository {

    suspend fun authorize(login: String, password: String)

    suspend fun createUser(login: String, password: String)
}