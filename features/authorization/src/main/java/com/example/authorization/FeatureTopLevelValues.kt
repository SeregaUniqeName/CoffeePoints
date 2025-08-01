package com.example.authorization

internal object FeatureTopLevelValues {

    val emailRegex = Regex("^[a-zA-Z0-9!#\$%&'*+/=?^_`{|}~.-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,3}\$")
    val passwordRegex = Regex("(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,8}")
}