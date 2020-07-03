package br.com.vineivel.mainlogin.state

import br.com.vineivel.domain.model.AuthRequest

sealed class MainLoginlUserEvent {
    data class LoginWithEmail(val authRequest: AuthRequest) : MainLoginlUserEvent()
}