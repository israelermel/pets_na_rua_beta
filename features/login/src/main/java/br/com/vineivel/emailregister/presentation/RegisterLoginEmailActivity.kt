package br.com.vineivel.emailregister.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.emailregister.R
import br.com.vineivel.emailregister.databinding.LoginBinding
import br.com.vineivel.emailregister.state.RegisterUserState
import br.com.vineivel.emailregister.state.toStateResource
import org.koin.android.viewmodel.ext.android.getViewModel

class RegisterLoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var viewModel: RegisterLoginEmailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        viewModel = getViewModel()
        binding.viewModel = viewModel

        configFields()
        attachObservers()
    }

    private fun configFields() {}

    private fun attachObservers() {
        with(viewModel) {

            resultState.observe(this@RegisterLoginEmailActivity, Observer { response ->
                when (response) {
                    is RegisterUserState.Authenticated -> {
                        Toast.makeText(
                            this@RegisterLoginEmailActivity, "Salvo com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is RegisterUserState.Error -> {
                        showErrorMessage(response)
                    }

                }
            })
        }
    }

    private fun RegisterLoginEmailViewModel.showErrorMessage(
        response: RegisterUserState.Error
    ) {
        val errorMessage = getString(response.toStateResource().message)

        when (response.error) {
            is AuthException.EmptyFullnameException -> {
                updateErrorMessageFullname(errorMessage)
            }
            is AuthException.EmptyEmailException -> {
                updateErrorMessageEmail(errorMessage)
            }
            is AuthException.InvalidEmailFormatException -> {
                updateErrorMessageEmail(errorMessage)
            }
            is AuthException.EmptyPasswordException -> {
                updateErrorMessagePassword(errorMessage)
            }
            is AuthException.PasswordUnderSixCharactersException -> {
                updateErrorMessagePassword(errorMessage)
            }
            is AuthException.PasswordsDoNotMatchException -> {
                updateErrorMessagePassword(errorMessage)
            }
            is AuthException.AlreadyRegisteredUserException -> {
                showErrorState(errorMessage)
            }
        }
    }

    private fun showErrorState(msg: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(msg)
            .setCancelable(true)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}
