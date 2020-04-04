package br.com.vineivel.data.di

import br.com.vineivel.data.auth.AuthRepository
import br.com.vineivel.domain.services.AuthService
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val dataModule = module {
    // Auth
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<AuthService> {
        AuthRepository(
            firebaseAuth = get()
        )
    }
}