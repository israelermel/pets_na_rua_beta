package br.com.vineivel.emailregister.presentation

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.User
import br.com.vineivel.emailregister.databinding.RegisterEmailBinding
import br.com.vineivel.emailregister.state.RegisterUserState
import br.com.vineivel.emailregister.state.toStateResource
import org.koin.android.viewmodel.ext.android.getViewModel

class RegisterLoginEmailActivity : AppCompatActivity() {

    private val binding by lazy {
        RegisterEmailBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy<RegisterLoginEmailViewModel> {
        getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()

        configFields()
        attachObservers()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configFields() {}

    private fun attachObservers() {
        with(viewModel) {

            resultState.observe(this@RegisterLoginEmailActivity, Observer { response ->
                when (response) {
                    is RegisterUserState.Error -> {
                        showErrorMessage(response)
                    }
                }
            })

            userLogged.observe(this@RegisterLoginEmailActivity, Observer { response ->
                setResultGoogle(response)
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

    private fun setResultGoogle(userLogged: User) {
        val intent = Intent()
        intent.putExtra("data", userLogged)
        setResult(Activity.RESULT_OK, intent)
        finish()
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
