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
        if (user.name.isEmpty())
            throw AuthException.EmptyFormValueException

        if (!EmailValidator(user.email))
            throw AuthException.InvalidEmailFormatException

        if (!PasswordlValidator(password))
            throw AuthException.PasswordUnderSixCharactersException

        if (password != passwordConfirmation)
            throw AuthException.PasswordsDoNotMatchException
    }
}