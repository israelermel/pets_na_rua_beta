package br.com.vineivel.login.presentation

sealed class UserEvent {
    data class SearchButtonClicked(val userBo: UserBo) : UserEvent()
    data class LoginButtonClicked(val userBo: UserBo) : UserEvent()
}
