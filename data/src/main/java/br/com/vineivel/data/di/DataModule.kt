package br.com.vineivel.data.di

import br.com.vineivel.data.auth.AuthEmailRepository
import br.com.vineivel.domain.services.AuthService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val dataModule = module {
    // Auth
    single { Firebase.auth }
    single<AuthService> {
        AuthEmailRepository(
            firebaseAuth = get()
        )
    }
}