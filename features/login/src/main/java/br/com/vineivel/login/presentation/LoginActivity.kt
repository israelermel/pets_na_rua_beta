package br.com.vineivel.login.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.vineivel.login.R
import br.com.vineivel.login.databinding.LoginBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var model: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        model = getViewModel()
        binding.viewModel = model

        attachObservers()
    }

    private fun attachObservers() {

    }
}
