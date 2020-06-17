package br.com.vineivel.emailregister.state

import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.emailregister.R

data class RegisterUserErrorStateResources(val message: Int) {
    companion object {
        operator fun invoke(error: AuthException) =
            when (error) {
                is AuthException.EmptyFormValueException -> RegisterUserErrorStateResources(
                    R.string.auth_form_empty
                )
                is AuthException.UserNotFoundException -> RegisterUserErrorStateResources(
                    R.string.auth_user_not_found
                )
                is AuthException.InvalidEmailFormatException -> RegisterUserErrorStateResources(
                    R.string.auth_email_bad_format
                )
                is AuthException.PasswordUnderSixCharactersException -> RegisterUserErrorStateResources(
                    R.string.auth_invalid_password
                )
                is AuthException.WrongCredentialsException -> RegisterUserErrorStateResources(
                    R.string.auth_wrong_credentials
                )
                is AuthException.AlreadyRegisteredUserException -> RegisterUserErrorStateResources(
                    R.string.auth_user_already_registered
                )
                is AuthException.PasswordsDoNotMatchException -> RegisterUserErrorStateResources(
                    R.string.auth_password_dont_match
                )
                else -> RegisterUserErrorStateResources(
                    R.string.auth_unknown_error
                )
            }
    }
}

fun RegisterUserState.Error.toStateResource() = RegisterUserErrorStateResources(error)