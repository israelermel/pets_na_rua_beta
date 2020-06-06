package br.com.vineivel.login

sealed class LoadingState {
    object Loading : LoadingState()
    object UnLoad : LoadingState()
}