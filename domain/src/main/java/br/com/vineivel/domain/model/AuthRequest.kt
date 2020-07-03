package br.com.vineivel.domain.model

import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.validation.EmailValidator

data class AuthRequest(
    val type: AuthRequestType = AuthRequestType.EMAIL,
    val email: String,
    var password: String
) {
    fun validateOrThrow() {
        if (email.isEmpty()) throw AuthException.EmptyEmailException

        if (!EmailValidator(email))
            throw AuthException.InvalidEmailFormatException

        if (password.isEmpty()) throw AuthException.EmptyPasswordException
    }
}

enum class AuthRequestType {
    EMAIL
}
