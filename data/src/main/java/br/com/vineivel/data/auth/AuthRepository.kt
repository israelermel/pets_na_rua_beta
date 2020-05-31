package br.com.vineivel.data.auth

import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.AuthRequest
import br.com.vineivel.domain.model.RegisterUser
import br.com.vineivel.domain.model.User
import br.com.vineivel.domain.services.AuthService
import com.google.firebase.auth.*
import io.reactivex.Single

class AuthRepository(val firebaseAuth: FirebaseAuth) : AuthService {

    override fun fetchLoggedUser(): Single<User?> {
        return Single.create { emitter ->
            firebaseAuth.currentUser?.let {
                emitter.onSuccess(
                    User(
                        it.uid,
                        it.displayName.orEmpty(),
                        it.email
                    )
                )
            } ?: emitter.onError(AuthException.UserNotFoundException)
        }
    }

    override fun registerUser(registerUser: RegisterUser): Single<Boolean> {
        try {
            return Single.create { emitter ->
                firebaseAuth.createUserWithEmailAndPassword(
                    registerUser.user.email!!,
                    registerUser.password
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser

                        user?.apply {
                            val profileChangeRequest = UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(registerUser.user.name)
                                .build()

                            updateProfile(profileChangeRequest).addOnCompleteListener {
                                emitter.onSuccess(true)
                            }
                        }

                        emitter.onSuccess(false)
                    } else {
                        emitter.onError(AuthException.UnknownAuthException)
                    }
                }

            }

        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthWeakPasswordException -> throw AuthException.PasswordUnderSixCharactersException
                else -> throw AuthException.UnknownAuthException
            }
        }
    }

    override fun login(user: AuthRequest): Single<Boolean> {
        try {
            return Single.create { emitter ->
                firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onSuccess(task.result?.user != null)
                        } else {
                            emitter.onError(AuthException.UnknownAuthException)
                        }
                    }
            }

        } catch (e: Exception) {
            when (e) {
                is IllegalArgumentException -> throw AuthException.EmptyFormValueException
                is FirebaseAuthInvalidCredentialsException -> throw AuthException.InvalidEmailFormatException
                is FirebaseAuthInvalidUserException -> throw AuthException.UserNotFoundException
                else -> throw AuthException.UnknownAuthException
            }
        }
    }

    override fun logout() = firebaseAuth.signOut()
}