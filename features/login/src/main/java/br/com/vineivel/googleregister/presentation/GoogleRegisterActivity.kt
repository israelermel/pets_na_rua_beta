package br.com.vineivel.googleregister.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.vineivel.domain.model.User
import br.com.vineivel.emailregister.databinding.GoogleRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import org.koin.android.viewmodel.ext.android.getViewModel

class GoogleRegisterActivity : AppCompatActivity() {


    private val googleSignInClient by lazy {
        GoogleSignIn.getClient(this, viewModel.gso)
    }

    private val signInIntent by lazy {
        googleSignInClient.signInIntent
    }

    private val binding by lazy {
        GoogleRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy<GoogleRegisterViewModel> {
        getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.signState.observe(this, Observer {
            if (it) {
                setResultGoogle(viewModel.getUser())
            }
        })

        signIn()

    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                try {
                    val account = task.getResult(ApiException::class.java)!!
                    viewModel.firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Log.e(TAG, e.message.orEmpty())
                }
            }
        }

    private fun setResultGoogle(userLogged: User) {
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
    }
}