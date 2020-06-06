package br.com.vineivel.petsnarua.di.modules

import br.com.vineivel.domain.usecases.register.RegisterLoginEmailUseCase
import org.koin.dsl.module

val useCaseModule = module {

    //Auth
    factory { RegisterLoginEmailUseCase(get()) }
}