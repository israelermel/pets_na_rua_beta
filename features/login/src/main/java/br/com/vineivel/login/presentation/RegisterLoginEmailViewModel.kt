package br.com.vineivel.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vineivel.domain.RequestResutl
import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.RegisterLogin
import br.com.vineivel.domain.usecases.register.RegisterLoginEmailUseCase
import br.com.vineivel.login.state.RegisterUserState
import kotlinx.coroutines.launch

class RegisterLoginEmailViewModel(
    private val emailUseCase: RegisterLoginEmailUseCase
) : ViewModel() {

    private val _resultState = MutableLiveData<RegisterUserState>()

    val resultState: LiveData<RegisterUserState>
        get() = _resultState

    fun registerLoginWithUsernameAndEmail(registerLogin: RegisterLogin) {
        viewModelScope.launch {

            when (val authResult = emailUseCase.execute(registerLogin)) {
                is RequestResutl.Success -> {
                    _resultState.postValue(RegisterUserState.Authenticated)
                }

                is RequestResutl.Failure -> {
                    _resultState.postValue(
                        RegisterUserState.Error(
                            error = authResult.throwable as AuthException
                        )
                    )
                }
            }
        }
    }

}