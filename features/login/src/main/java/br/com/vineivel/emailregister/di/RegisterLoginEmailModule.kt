package br.com.vineivel.emailregister.di

import br.com.vineivel.domain.usecases.register.RegisterLoginEmailUseCase
import br.com.vineivel.emailregister.presentation.RegisterLoginEmailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
//    factory { LoginRepository() }
    factory { RegisterLoginEmailUseCase(get()) }
    viewModel { RegisterLoginEmailViewModel(get()) }
}