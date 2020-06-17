package br.com.vineivel.emailregister.state

import br.com.vineivel.domain.model.RegisterLogin

sealed class RegisterLoginUserEvent {
    data class RegisterLoginWithNameAndEmail(val registerLogin: RegisterLogin) :
        RegisterLoginUserEvent()
}
