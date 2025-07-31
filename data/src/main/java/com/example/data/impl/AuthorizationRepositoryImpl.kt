package com.example.data.impl

import com.example.data.RequestException
import com.example.data.TOKEN_ALIAS
import com.example.data.UserAlreadyExistException
import com.example.data.UserDoNotExistException
import com.example.data.api.AuthorizationRepository
import com.example.data.local.EncryptedStore
import com.example.data.local.TokenLifetimeStore
import com.example.data.network.models.AuthApiService
import com.example.data.network.models.UserPost
import jakarta.inject.Inject
import retrofit2.HttpException

class AuthorizationRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val encryptedStore: EncryptedStore,
    private val lifetimeStore: TokenLifetimeStore,
) : AuthorizationRepository {

    init {
        encryptedStore.createKey(TOKEN_ALIAS)
        encryptedStore.createKey(PASSWORD_ALIAS)
    }

    override suspend fun authorize(login: String, password: String) {

        val encryptedPassword = encryptString(password, PASSWORD_ALIAS)
        try {
            val user = UserPost(login, encryptedPassword)
            val result = apiService.authorize(user)
            val token = result.token
            val lifetime = result.tokenLifeTime
            val encryptedToken = encryptString(token, TOKEN_ALIAS)
            lifetimeStore.save(encryptedToken, System.currentTimeMillis(), lifetime)
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
        val encryptedPassword = encryptString(password, PASSWORD_ALIAS)
        val user = UserPost(login, encryptedPassword)
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

    private fun encryptString(input: String, alias: String): String {
        return encryptedStore.encrypt(input, alias)
    }

    companion object {
        private const val PASSWORD_ALIAS = "passwordAlias"
    }
}