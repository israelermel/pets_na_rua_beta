package br.com.vineivel.mainlogin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.vineivel.domain.LoadingState

class MainLoginViewModel : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _registerEmailClick = MutableLiveData<Boolean>()
    val registerEmailClick: LiveData<Boolean>
        get() = _registerEmailClick

    private val _registerGoogleClick = MutableLiveData<Boolean>()
    val registerGooleClick: LiveData<Boolean>
        get() = _registerGoogleClick


    init {
        _loadingState.postValue(LoadingState.Loaded)
    }

    fun onRegisterEmailClick() {
        _registerEmailClick.postValue(true)
    }

    fun onRegisterGoogleClick() {
        _registerGoogleClick.postValue(true)
    }

}