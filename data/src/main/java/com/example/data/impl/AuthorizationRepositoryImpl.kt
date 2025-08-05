package com.example.data.impl

import com.example.data.RequestException
import com.example.data.UserAlreadyExistException
import com.example.data.UserDoNotExistException
import com.example.data.api.AuthorizationRepository
import com.example.data.local.TokenLifetimeStore
import com.example.data.network.AuthApiService
import com.example.data.network.models.UserPost
import jakarta.inject.Inject
import retrofit2.HttpException

class AuthorizationRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val lifetimeStore: TokenLifetimeStore,
) : AuthorizationRepository {

    override suspend fun authorize(login: String, password: String) {
        try {
            val user = UserPost(login, password)
            val result = apiService.authorize(user)
            val token = result.token
            lifetimeStore.save(token)
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> throw RequestException(
                    body = e.message!!
                )
                404 -> throw UserDoNotExistException(
                    login = login
                )
                else -> throw e
            }
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun createUser(login: String, password: String) {
        val user = UserPost(login, password)
        try {
            apiService.register(user)
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> throw RequestException(
                    body = e.message!!
                )

                406 -> throw UserAlreadyExistException(
                    login = login,
                )

                else -> throw e
            }
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        private const val PASSWORD_ALIAS = "passwordAlias"
    }
}