package br.com.vineivel.domain.errors

sealed class AuthException : RuntimeException() {
    object UserNotFoundException : AuthException()
    object EmptyFullnameException : AuthException()
    object EmptyEmailException : AuthException()
    object EmptyPasswordException : AuthException()
    object WrongCredentialsException : AuthException()
    object NoAuthSessionFoundException : AuthException()
    object PasswordUnderSixCharactersException : AuthException()
    object PasswordsDoNotMatchException : AuthException()
    object UnknownAuthException : AuthException()
    object AlreadyRegisteredUserException : AuthException()
    object InvalidEmailFormatException : AuthException()
}