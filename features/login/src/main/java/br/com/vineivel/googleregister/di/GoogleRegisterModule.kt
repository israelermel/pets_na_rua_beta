package br.com.vineivel.googleregister.di

import br.com.vineivel.googleregister.presentation.GoogleRegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val googleRegisterModule = module {
    viewModel { GoogleRegisterViewModel() }
}