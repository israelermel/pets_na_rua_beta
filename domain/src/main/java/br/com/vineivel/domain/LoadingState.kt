package br.com.vineivel.domain

sealed class LoadingState {
    object Loading : LoadingState()
    object Loaded : LoadingState()
}