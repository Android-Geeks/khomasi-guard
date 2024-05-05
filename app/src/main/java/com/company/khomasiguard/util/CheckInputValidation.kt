package com.company.khomasiguard.util

object CheckInputValidation {
    fun isEmailValid(email: String) =
        email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

    fun isPasswordValid(password: String) =
        password.matches(Regex("^(?=.*[0-9])(?=.*[!@#$%^&*_])(?=\\S+\$).{8,}\$"))
}