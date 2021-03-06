package br.com.vineivel.emailregister.state

import br.com.vineivel.domain.errors.AuthException

sealed class RegisterUserState {
    object Loading : RegisterUserState()
    object Unauthenticated : RegisterUserState()
    object Authenticated : RegisterUserState()
    data class Error(val error: AuthException) : RegisterUserState()
}