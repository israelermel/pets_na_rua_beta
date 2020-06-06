package br.com.vineivel.data.auth

import br.com.vineivel.domain.errors.AuthException
import br.com.vineivel.domain.model.AuthRequest
import br.com.vineivel.domain.model.RegisterLogin
import br.com.vineivel.domain.model.User
import br.com.vineivel.domain.services.AuthService
import com.google.firebase.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository(val firebaseAuth: FirebaseAuth) : AuthService {

    override suspend fun fetchLoggedUser() = firebaseAuth.currentUser?.let {
        User(
            it.uid,
            it.displayName.orEmpty(),
            it.email
        )
    }

    override suspend fun registerUser(registerLogin: RegisterLogin) = withContext(Dispatchers.IO) {
        try {
            // Create user
            firebaseAuth.createUserWithEmailAndPassword(
                registerLogin.user.email!!, registerLogin.password
            ).await()

            // Get user
            val currentUser = firebaseAuth.currentUser

            // Update informations
            currentUser?.apply {
                val profileChangeRequest = UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(registerLogin.user.name)
                    .build()

                updateProfile(profileChangeRequest).await()
            }

            currentUser != null
        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthWeakPasswordException -> throw AuthException.PasswordUnderSixCharactersException
                else -> throw AuthException.UnknownAuthException
            }

        }
    }

    override suspend fun login(user: AuthRequest) = withContext(Dispatchers.IO) {
        try {
            val authResult =
                firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
            authResult?.user != null
        } catch (e: Exception) {
            when (e) {
                is IllegalArgumentException -> throw AuthException.EmptyFormValueException
                is FirebaseAuthInvalidCredentialsException -> throw AuthException.InvalidEmailFormatException
                is FirebaseAuthInvalidUserException -> throw AuthException.UserNotFoundException
                else -> throw AuthException.UnknownAuthException
            }
        }
    }

    override suspend fun logout() = firebaseAuth.signOut()
}