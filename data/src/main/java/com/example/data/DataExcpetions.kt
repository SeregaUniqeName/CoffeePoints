package com.example.data

class UserDoNotExistException(
    private val login: String,
    override val message: String = "User $login doesn't exist!"
) : Exception()

class RequestException(
    private val body: String,
    override val message: String = "Response Exception \n $body"
) : Exception()

class TokenOutOfTimeException() : Exception()

class UserAlreadyExistException(
    private val login: String,
    override val message: String = "Login $login is already exist!",
) : Exception()