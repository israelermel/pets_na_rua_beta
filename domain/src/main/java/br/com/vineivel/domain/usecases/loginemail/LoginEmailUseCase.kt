package br.com.vineivel.domain.usecases.loginemail

import br.com.vineivel.domain.RequestResult
import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.AuthRequest
import br.com.vineivel.domain.model.User
import br.com.vineivel.domain.services.AuthService

class LoginEmailUseCase(private val authService: AuthService) {

    suspend fun execute(authRequest: AuthRequest): RequestResult<User> {
        try {
            authRequest.validateOrThrow()

            if (authService.login(authRequest)) {
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