package br.com.vineivel.login.presentation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.vineivel.login.R
import br.com.vineivel.login.databinding.LoginBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        viewModel = getViewModel()
        binding.viewModel = viewModel

        configFields()
        attachObservers()
    }

    private fun configFields() {
        with(binding) {
            btnLogin.setOnClickListener {
                val password = edtPassword.text.toString()
                val userName = edtPassword.text.toString()

                actionOnEvent(UserEvent.SearchButtonClicked(UserBo(userName, password)))
//                viewModel?.login(UserBo(userName, password))
            }
        }

    }

    private fun attachObservers() {
        with(viewModel) {
            loginRequest.observe(this@LoginActivity, Observer { response ->
                when (response) {
                    is LoginUseCase.LoginResult.Success -> {
                        Log.d("israel", "Success")
                    }
                    is LoginUseCase.LoginResult.ServerError -> {
                        Log.d("israel", "ServerError")
                    }
                    is LoginUseCase.LoginResult.NetworkRelated -> {
                        Log.d("israel", "NetworkException")
                    }
                    is LoginUseCase.LoginResult.UnexpectedError -> {
                        Log.d("israel", "UnexpectedError")
                    }
                    is LoginUseCase.LoginResult.ConnectionError -> {
                        Log.d("israel", "ConnectionError")
                    }
                    is LoginUseCase.LoginResult.InvalidUser -> {
                        Log.d("israel", "InvalidUser")
                    }
                }
            })
        }
    }

    fun actionOnEvent(event: UserEvent) {
        when (event) {
            is UserEvent.SearchButtonClicked -> {
                render(LoginUseCase.SearchViewState.LoadingState)
                Handler().postDelayed({
                    render(viewModel.searchAction(event.userBo))
                }, 3000)

            }
        }
    }

    fun render(state: LoginUseCase.SearchViewState) {
        when (state) {
            LoginUseCase.SearchViewState.LoadingState -> showLoadingState()
            LoginUseCase.SearchViewState.HideLoadingState -> hideLoadingState()
            is LoginUseCase.SearchViewState.PullToRefreshState -> showLoadingState()
            is LoginUseCase.SearchViewState.DataState -> showDataState(state.userBo)
            is LoginUseCase.SearchViewState.ErrorState -> showErrorState(state.error)
        }
    }

    private fun showLoadingState() {
        binding.loginProgressBar.show()
    }

    private fun hideLoadingState() {
        binding.loginProgressBar.hide()
    }

    private fun showDataState(userBo: UserBo) {
        render(LoginUseCase.SearchViewState.HideLoadingState)
        Toast.makeText(this, "Bem vindo : ${userBo.userName}",Toast.LENGTH_LONG).show()
    }

    private fun showErrorState(msg: String) {
        render(LoginUseCase.SearchViewState.HideLoadingState)
        Toast.makeText(this, "Houve um erro mensagem : $msg",Toast.LENGTH_LONG).show()
    }

}
