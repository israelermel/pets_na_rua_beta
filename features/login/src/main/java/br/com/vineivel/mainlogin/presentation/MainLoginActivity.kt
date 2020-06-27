package br.com.vineivel.mainlogin.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.vineivel.emailregister.contract.EmailRegisterContract
import br.com.vineivel.emailregister.databinding.MainLoginBinding
import br.com.vineivel.googleregister.contract.GoogleRegisterContract
import org.koin.android.viewmodel.ext.android.getViewModel


class MainLoginActivity : AppCompatActivity() {

    private val binding by lazy {
        MainLoginBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy<MainLoginViewModel> {
        getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()

        with(viewModel) {
            registerEmailClick.observe(this@MainLoginActivity, Observer {
                if (it) {
                    emailRegisterContract.launch(0)
                }
            })

            registerGooleClick.observe(this@MainLoginActivity, Observer {
                if (it) {
                    googleRegisterContract.launch(0)
                }
            })
        }
    }

    private val googleRegisterContract =
        registerForActivityResult(GoogleRegisterContract()) { result ->
            if (result != null) {
                Toast.makeText(this, "${result.profilePicture}", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private val emailRegisterContract =
        registerForActivityResult(EmailRegisterContract()) { result ->
            if (result != null) {
                Toast.makeText(this, "${result.profilePicture}", Toast.LENGTH_LONG)
                    .show()
            }
        }
}