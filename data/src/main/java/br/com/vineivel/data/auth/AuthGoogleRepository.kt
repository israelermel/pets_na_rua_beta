package br.com.vineivel.data.auth

import br.com.vineivel.domain.model.AuthRequest
import br.com.vineivel.domain.model.RegisterLogin
import br.com.vineivel.domain.model.User
import br.com.vineivel.domain.services.AuthService

class AuthGoogleRepository : AuthService {

    override suspend fun fetchLoggedUser(): User? {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(registerLogin: RegisterLogin): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun login(user: AuthRequest): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

}