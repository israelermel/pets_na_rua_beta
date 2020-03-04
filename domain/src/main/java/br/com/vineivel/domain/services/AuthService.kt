package br.com.vineivel.domain.services

import br.com.vineivel.domain.model.AuthRequest
import br.com.vineivel.domain.model.RegisterUser
import br.com.vineivel.domain.model.User
import io.reactivex.Single

interface AuthService {
    fun fetchLoggedUser(): Single<User?>
    fun registerUser(registerUser: RegisterUser): Single<Boolean>
    fun login(user: AuthRequest): Single<Boolean>
    fun logout()
}