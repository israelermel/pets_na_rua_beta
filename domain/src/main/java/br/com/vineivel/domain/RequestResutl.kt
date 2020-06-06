package br.com.vineivel.domain

sealed class RequestResutl<out T> {
    data class Success<T>(val result: T) : RequestResutl<T>()
    data class Failure(val throwable: Throwable) : RequestResutl<Nothing>()
}