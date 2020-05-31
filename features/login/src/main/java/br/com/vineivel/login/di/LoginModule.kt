package br.com.vineivel.login.di

import br.com.vineivel.login.presentation.LoginRepository
import br.com.vineivel.login.presentation.LoginUseCase
import br.com.vineivel.login.presentation.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory { LoginRepository() }
    factory { LoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}