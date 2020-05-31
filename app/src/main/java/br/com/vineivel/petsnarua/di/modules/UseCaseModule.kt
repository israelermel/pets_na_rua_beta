package br.com.vineivel.petsnarua.di.modules

import br.com.vineivel.domain.usecases.login.RegisterUserUseCase
import org.koin.dsl.module

val useCaseModule = module {

    //Auth
    factory { RegisterUserUseCase(get()) }
}