package br.com.vineivel.domain.usecases.register

import br.com.vineivel.domain.RequestResult
import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.RegisterLogin
import br.com.vineivel.domain.model.User
import br.com.vineivel.domain.services.AuthService

class RegisterLoginEmailUseCase(private val authService: AuthService) {

    suspend fun execute(registerLogin: RegisterLogin): RequestResult<User> {
        try {
            registerLogin.validateOrThrow()

            if (authService.registerUser(registerLogin)) {
                return RequestResult.Success(authService.fetchLoggedUser()!!)
            }
        } catch (exception: Throwable) {
            return RequestResult.Failure(
                if (exception is AuthException) exception
                else AuthException.UnknownAuthException
            )
        }

        return RequestResult.Failure(AuthException.UnknownAuthException)
    }
}