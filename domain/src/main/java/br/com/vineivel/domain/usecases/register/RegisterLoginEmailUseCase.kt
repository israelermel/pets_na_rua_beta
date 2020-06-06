package br.com.vineivel.domain.usecases.register

import br.com.vineivel.domain.RequestResutl
import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.RegisterLogin
import br.com.vineivel.domain.model.User
import br.com.vineivel.domain.services.AuthService

class RegisterLoginEmailUseCase(private val authService: AuthService) {

    suspend fun execute(registerLogin: RegisterLogin): RequestResutl<User> {
        try {
            registerLogin.validateOrThrow()

            if (authService.registerUser(registerLogin)) {
                return RequestResutl.Success(authService.fetchLoggedUser()!!)
            }
        } catch (exception: Throwable) {
            return RequestResutl.Failure(
                if (exception is AuthException) exception
                else AuthException.UnknownAuthException
            )
        }

        return RequestResutl.Failure(AuthException.UnknownAuthException)
    }
}