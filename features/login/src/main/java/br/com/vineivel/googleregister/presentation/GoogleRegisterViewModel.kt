package br.com.vineivel.googleregister.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleRegisterViewModel : ViewModel() {

    val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("637949171681-4khcsbrrql9pqttjp40elck4lqs27uj1.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    private val auth by lazy {
        Firebase.auth
    }

    val userLogged by lazy {
        auth.currentUser
    }

    val _signState = MutableLiveData<Boolean>()
    val signState: LiveData<Boolean>
        get() = _signState

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _signState.postValue(true)
                } else {
                    _signState.postValue(false)
                }
            }
    }

}