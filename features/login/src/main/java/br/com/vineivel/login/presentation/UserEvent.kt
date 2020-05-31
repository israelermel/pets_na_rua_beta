package br.com.vineivel.login.presentation

sealed class UserEvent {
    object SearchInput : UserEvent()
    data class SearchButtonClicked(val userBo: UserBo) : UserEvent()
}
