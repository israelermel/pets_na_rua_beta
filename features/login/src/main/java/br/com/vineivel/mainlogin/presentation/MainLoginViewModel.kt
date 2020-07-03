package br.com.vineivel.mainlogin.presentation

import androidx.lifecycle.*
import br.com.vineivel.domain.LoadingState
import br.com.vineivel.domain.RequestResult
import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.AuthRequest
import br.com.vineivel.domain.model.AuthRequestType
import br.com.vineivel.domain.usecases.loginemail.LoginEmailUseCase
import br.com.vineivel.emailregister.state.RegisterUserState
import br.com.vineivel.extensions.ErrorMessageState
import br.com.vineivel.mainlogin.state.MainLoginlUserEvent
import kotlinx.coroutines.launch

class MainLoginViewModel(
    private val loginEmailUseCase: LoginEmailUseCase
) : ViewModel() {


    // Form EditText Fields
    val emailText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()

    // Error Email
    private val _errorMessageEmail = MutableLiveData<String>()
    val errorMessageEmail: LiveData<String>
        get() = _errorMessageEmail

    val errorMessageEmailState = Transformations.map(emailText) {
        if (it.trim().isNotEmpty()) ErrorMessageState.HIDE else ErrorMessageState.SHOW
    }

    // Error Password
    private val _errorMessagePassword = MutableLiveData<String>()
    val errorMessagePassword: LiveData<String>
        get() = _errorMessagePassword

    val errorMessagePasswordState = Transformations.map(passwordText) {
        if (it.trim().isNotEmpty()) ErrorMessageState.HIDE else ErrorMessageState.SHOW
    }

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _registerEmailClick = MutableLiveData<Boolean>()
    val registerEmailClick: LiveData<Boolean>
        get() = _registerEmailClick

    private val _registerGoogleClick = MutableLiveData<Boolean>()
    val registerGooleClick: LiveData<Boolean>
        get() = _registerGoogleClick

    private val _resultState = MutableLiveData<RegisterUserState>()
    val resultState: LiveData<RegisterUserState>
        get() = _resultState

    init {
        _loadingState.postValue(LoadingState.Loaded)
    }

    fun onRegisterEmailClick() {
        _registerEmailClick.postValue(true)
    }

    fun onRegisterGoogleClick() {
        _registerGoogleClick.postValue(true)
    }

    fun onLoginEmailClick() {
        val userEvent = createAuthRequest()
        actionOnEvent(MainLoginlUserEvent.LoginWithEmail(userEvent))
    }

    fun loginEmailExecute(authRequest: AuthRequest) {
        viewModelScope.launch {
            loadingStateToLoading()

            when (val authRequest = loginEmailUseCase.execute(authRequest)) {
                is RequestResult.Success -> {
                    loadingStateToLoaded()
                    _resultState.postValue(RegisterUserState.Authenticated)
                }

                is RequestResult.Failure -> {
                    loadingStateToLoaded()
                    errorResultState(authRequest.throwable as AuthException)
                }
            }
        }
    }

    private fun errorResultState(authException: AuthException) {
        _resultState.postValue(
            RegisterUserState.Error(
                error = authException
            )
        )
    }

    private fun actionOnEvent(userEvent: MainLoginlUserEvent) {
        when (userEvent) {
            is MainLoginlUserEvent.LoginWithEmail -> {
                loginEmailExecute(userEvent.authRequest)
            }
        }
    }

    private fun createAuthRequest(): AuthRequest {
        return AuthRequest(
            AuthRequestType.EMAIL,
            emailText.value.orEmpty(),
            passwordText.value.orEmpty()
        )
    }

    private fun loadingStateToLoading() {
        _loadingState.postValue(LoadingState.Loading)
    }

    private fun loadingStateToLoaded() {
        _loadingState.postValue(LoadingState.Loaded)
    }

    fun updateEmailErrorMessage(errorMessage: String) {
        _errorMessageEmail.postValue(errorMessage)
    }

    fun updatePasswordErrorMessage(errorMessage: String) {
        _errorMessagePassword.postValue(errorMessage)
    }

}