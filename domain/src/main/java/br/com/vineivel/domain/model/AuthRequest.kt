package br.com.vineivel.domain.model

import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.validation.EmailValidator

data class AuthRequest(
    val type: AuthRequestType = AuthRequestType.EMAIL,
    val email: String,
    var password: String
) {
    fun validateOrThrow() {
        if (!EmailValidator(email))
            throw AuthException.InvalidEmailFormatException
    }
}

enum class AuthRequestType {
    EMAIL
}
