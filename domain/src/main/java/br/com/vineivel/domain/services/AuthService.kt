package br.com.vineivel.domain.services

import br.com.vineivel.domain.model.AuthRequest
import br.com.vineivel.domain.model.RegisterLogin
import br.com.vineivel.domain.model.User

interface AuthService {
    suspend fun fetchLoggedUser(): User?
    suspend fun registerUser(registerLogin: RegisterLogin): Boolean
    suspend fun login(user: AuthRequest): Boolean
    suspend fun logout()
}