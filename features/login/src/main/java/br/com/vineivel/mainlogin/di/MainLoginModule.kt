package br.com.vineivel.mainlogin.di

import br.com.vineivel.mainlogin.presentation.MainLoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainLoginModule = module {
    viewModel { MainLoginViewModel() }
}