package br.com.vineivel.emailregister.presentation

import androidx.lifecycle.*
import br.com.vineivel.domain.LoadingState
import br.com.vineivel.domain.RequestResult
import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.RegisterLogin
import br.com.vineivel.domain.model.User
import br.com.vineivel.domain.usecases.register.RegisterLoginEmailUseCase
import br.com.vineivel.emailregister.state.RegisterLoginUserEvent
import br.com.vineivel.emailregister.state.RegisterUserState
import br.com.vineivel.extensions.ErrorMessageState
import kotlinx.coroutines.launch

class RegisterLoginEmailViewModel(
    private val emailUseCase: RegisterLoginEmailUseCase
) : ViewModel() {

    private val _resultState = MutableLiveData<RegisterUserState>()

    val resultState: LiveData<RegisterUserState>
        get() = _resultState

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    // Form EditText Fields
    val fullNameText = MutableLiveData<String>()
    val emailText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()
    val confirmPasswordText = MutableLiveData<String>()

    // Error Fullname
    private val _errorMessageFullname = MutableLiveData<String>()
    val errorMessageFullname: LiveData<String>
        get() = _errorMessageFullname

    val errorMessageFullnameState = Transformations.map(fullNameText) {
        if (it.trim().isNotEmpty()) ErrorMessageState.HIDE else ErrorMessageState.SHOW
    }

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

    init {
        loadingStateToUnload()
    }

    // User Events
    private fun registerLoginWithUsernameAndEmail(registerLogin: RegisterLogin) {
        viewModelScope.launch {
            loadingStateToLoading()

            when (val authResult = emailUseCase.execute(registerLogin)) {
                is RequestResult.Success -> {
                    authenticatedResultState()
                    loadingStateToUnload()
                }

                is RequestResult.Failure -> {
                    loadingStateToUnload()
                    errorResultState(authResult.throwable as AuthException)
                }
            }
        }
    }

    fun onRegisterLoginClick() {
        val registerLogin = createRegisterUser()
        actionOnEvent(
            RegisterLoginUserEvent.RegisterLoginWithNameAndEmail(registerLogin)
        )
    }

    private fun actionOnEvent(eventRegisterLogin: RegisterLoginUserEvent) {
        when (eventRegisterLogin) {
            is RegisterLoginUserEvent.RegisterLoginWithNameAndEmail -> {
                registerLoginWithUsernameAndEmail(eventRegisterLogin.registerLogin)
            }
        }
    }

    private fun createRegisterUser(): RegisterLogin {
        val password = passwordText.value.orEmpty()
        val confirmPassword = confirmPasswordText.value.orEmpty()
        val userName = fullNameText.value.orEmpty()
        val email = emailText.value.orEmpty()

        return RegisterLogin(
            User(name = userName, email = email),
            password = password,
            passwordConfirmation = confirmPassword
        )
    }

    // EditText Events

    fun updateErrorMessageFullname(errorMessage: String) {
        _errorMessageFullname.postValue(errorMessage)
    }

    fun updateErrorMessageEmail(errorMessage: String) {
        _errorMessageEmail.postValue(errorMessage)
    }

    fun updateErrorMessagePassword(errorMessage: String) {
        _errorMessagePassword.postValue(errorMessage)
    }

    // Loading State Events
    private fun authenticatedResultState() {
        _resultState.postValue(RegisterUserState.Authenticated)
    }

    private fun errorResultState(authException: AuthException) {
        _resultState.postValue(
            RegisterUserState.Error(
                error = authException
            )
        )
    }

    private fun loadingStateToLoading() {
        _loadingState.postValue(LoadingState.Loading)
    }

    private fun loadingStateToUnload() {
        _loadingState.postValue(LoadingState.UnLoad)
    }

}