package br.com.vineivel.login.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.vineivel.domain.LoadingState
import br.com.vineivel.domain.model.RegisterLogin
import br.com.vineivel.domain.model.User
import br.com.vineivel.login.R
import br.com.vineivel.login.databinding.LoginBinding
import br.com.vineivel.login.state.RegisterLoginUserEvent
import br.com.vineivel.login.state.RegisterUserState
import br.com.vineivel.login.state.toStateResource
import org.koin.android.viewmodel.ext.android.getViewModel

class RegisterLoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var emailViewModelRegister: RegisterLoginEmailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        emailViewModelRegister = getViewModel()
        binding.viewModel = emailViewModelRegister

        configFields()
        attachObservers()
    }

    private fun configFields() {
        with(binding) {
            btnRegisterLoginEmail.setOnClickListener {
                val registerUser = createRegisterUser()

                actionOnEvent(
                    RegisterLoginUserEvent.RegisterLoginWithNameAndEmail(registerUser)
                )
            }
        }
    }

    private fun LoginBinding.createRegisterUser(): RegisterLogin {
        val password = edtUserRegisterPassword.text.toString()
        val confirmPassword = edtUserRegisterConfirmPassword.text.toString()
        val userName = edtUserRegisterFullName.text.toString()
        val email = edtUserRegisterEmail.text.toString()

        return RegisterLogin(
            User(name = userName, email = email),
            password = password,
            passwordConfirmation = confirmPassword
        )
    }

    private fun actionOnEvent(eventRegisterLogin: RegisterLoginUserEvent) {
        when (eventRegisterLogin) {
            is RegisterLoginUserEvent.RegisterLoginWithNameAndEmail -> {
                renderLoading(LoadingState.Loading)
                emailViewModelRegister.registerLoginWithUsernameAndEmail(eventRegisterLogin.registerLogin)
            }
        }
    }

    private fun attachObservers() {
        with(emailViewModelRegister) {

            resultState.observe(this@RegisterLoginEmailActivity, Observer { response ->
                when (response) {
                    is RegisterUserState.Loading -> showLoadingState()
                    is RegisterUserState.Authenticated -> {
                        renderLoading(LoadingState.UnLoad)

                        Toast.makeText(
                            this@RegisterLoginEmailActivity, "Salvo com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is RegisterUserState.Error -> {
                        renderLoading(LoadingState.UnLoad)
                        showErrorState(response.toStateResource().message)
                    }

                }
            })
        }
    }

    private fun showLoadingState() {
        binding.loginProgressBar.show()
    }

    private fun hideLoadingState() {
        binding.loginProgressBar.hide()
    }

    fun renderLoading(loadingState: LoadingState) {
        when (loadingState) {
            is LoadingState.Loading -> showLoadingState()
            is LoadingState.UnLoad -> hideLoadingState()
        }
    }

    private fun showErrorState(msg: Int) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(msg)
            .setCancelable(true)
            .setPositiveButton("Ok") { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

}
