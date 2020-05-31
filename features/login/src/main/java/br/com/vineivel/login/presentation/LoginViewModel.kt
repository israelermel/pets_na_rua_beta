package br.com.vineivel.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(
    private val useCase: LoginUseCase
) : ViewModel() {

    private val _loginRequest = MutableLiveData<LoginUseCase.LoginResult>()
    private val _loadingState = MutableLiveData<LoginUseCase.SearchViewState>()

    val loginRequest : LiveData<LoginUseCase.LoginResult>
        get() = _loginRequest

    val loadingState : LiveData<LoginUseCase.SearchViewState>
        get() = _loadingState

    fun login(userBo: UserBo) {
        _loginRequest.postValue(useCase.login(userBo))
    }

    fun searchAction(userBo: UserBo): LoginUseCase.SearchViewState {
        return useCase.searchAction(userBo)
    }

}