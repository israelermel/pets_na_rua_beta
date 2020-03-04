package br.com.vineivel.domain.validation

object PasswordlValidator {
    operator fun invoke(password: String?) = password?.let {
        password.length >= 6
    } ?: false
}