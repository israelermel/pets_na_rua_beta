package br.com.vineivel.googleregister.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.com.vineivel.emailregister.databinding.GoogleRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.ext.android.getViewModel

class GoogleRegisterActivity : AppCompatActivity() {

    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("637949171681-4khcsbrrql9pqttjp40elck4lqs27uj1.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    private val auth by lazy {
        Firebase.auth
    }

    private val googleSignInClient by lazy {
        GoogleSignIn.getClient(this, gso)
    }

    private val signInIntent by lazy {
        googleSignInClient.signInIntent
    }

    private val userLogged by lazy {
        auth.currentUser
    }

    private val binding by lazy {
        GoogleRegisterBinding.inflate(layoutInflater)
    }

    private val viewModelGoogleRegister by lazy<GoogleRegisterViewModel> {
        getViewModel()
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
//                GoogleSignIn.getLastSignedInAccount(this@GoogleRegisterActivity) // obter ultimo usuario logado
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Log.e(TAG, e.message.orEmpty())
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModelGoogleRegister
        binding.executePendingBindings()

    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setResultGoogle()
                } else {
                    Toast.makeText(
                        this@GoogleRegisterActivity,
                        "Error${task.exception?.message.orEmpty()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun setResultGoogle() {
        val intent = Intent()
        intent.putExtra("data", userLogged)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun signIn() {
        startForResult.launch(signInIntent)
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}