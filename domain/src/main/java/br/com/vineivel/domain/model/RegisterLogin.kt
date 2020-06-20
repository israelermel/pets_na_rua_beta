package br.com.vineivel.domain.model

import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.validation.EmailValidator
import br.com.vineivel.domain.validation.PasswordlValidator

data class RegisterLogin(
    val user: User,
    val password: String,
    val passwordConfirmation: String
) {
    fun validateOrThrow() {
        if (user.name.trim().isEmpty()) throw AuthException.EmptyFullnameException

        if (user.email.orEmpty().isEmpty()) throw AuthException.EmptyEmailException

        if (!EmailValidator(user.email)) throw AuthException.InvalidEmailFormatException

        if (password.isEmpty()) throw AuthException.EmptyPasswordException

        if (!PasswordlValidator(password)) throw AuthException.PasswordUnderSixCharactersException

        if (password != passwordConfirmation)
            throw AuthException.PasswordsDoNotMatchException
    }
}