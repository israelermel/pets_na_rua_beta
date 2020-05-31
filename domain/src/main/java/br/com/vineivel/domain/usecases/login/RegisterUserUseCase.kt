package br.com.vineivel.domain.usecases.login

import br.com.vineivel.domain.services.AuthService

class RegisterUserUseCase (private val authService: AuthService) {


//    fun execute(registerUser: RegisterUser): Result<User> {
//        try {
//            registerUser.validateOrThrow()
//
//            // Register user
//            authService.registerUser(registerUser)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
//
////            if (authService.registerUser(registerUser)) {
////                return Result.success(authService.fetchLoggedUser()!!)
////            }
//        } catch (exception: Throwable) {
//            return Result.Failure(
//                if (exception is AuthException) exception
//                else AuthException.UnknownAuthException
//            )
//        }
//
//        return Result.Failure(AuthException.UnknownAuthException)
//    }
}