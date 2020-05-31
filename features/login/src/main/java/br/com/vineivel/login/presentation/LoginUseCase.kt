package br.com.vineivel.login.presentation

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    fun login(userBo: UserBo) : LoginResult {
        return try {
            val response = loginRepository.loginRequest(userBo)
            if (response.isSuccessful) {
                LoginResult.Success(response.data)
            } else {
                when(response.code) {
                    408 -> LoginResult.ConnectionError
                    500 -> LoginResult.ServerError(response.errorMessage)
                    else -> LoginResult.UnexpectedError(response.code)
                }
            }
        } catch (e: Exception) {
            LoginResult.NetworkRelated(e)
        }
    }


    fun searchAction(userBo: UserBo): SearchViewState {
       return SearchViewState.ErrorState("error teste")
//        return SearchViewState.DataState(userBo)
    }

    sealed class LoginResult {
        data class Success(val data: String) : LoginResult()
        object InvalidUser : LoginResult()
        object ConnectionError : LoginResult()
        data class ServerError(val error: String) : LoginResult()
        data class UnexpectedError(val code: Int) : LoginResult()
        data class NetworkRelated(val cause: Exception) : LoginResult()
    }

    sealed class ApiExceptions(val cause: Throwable) {
        class NetworkRelated(cause: Throwable): ApiExceptions(cause)
        class Unexpected(cause: Throwable): ApiExceptions(cause)
        class Unauthorised(cause: Throwable): ApiExceptions(cause)
    }

    sealed class SearchViewState {
        object LoadingState : SearchViewState()
        object HideLoadingState : SearchViewState()
        data class ErrorState(val error: String) : SearchViewState()
        data class DataState(val userBo: UserBo) : SearchViewState()
    }
}