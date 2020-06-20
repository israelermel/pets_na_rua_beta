package br.com.vineivel.mainlogin.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.vineivel.emailregister.R
import br.com.vineivel.emailregister.presentation.RegisterLoginEmailActivity
import br.com.vineivel.facebookregister.contract.FacebookRegisterContract
import br.com.vineivel.googleregister.contract.GoogleRegisterContract
import com.facebook.AccessToken
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_login.*


class MainLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        btn_facebook_register_main_login.setOnClickListener {
            facebookRegisterContract.launch(0)
        }

        btn_google_register_main_login.setOnClickListener {
            googleRegisterContract.launch(0)
        }

        btn_register_email.setOnClickListener {
            startActivity(Intent(this, RegisterLoginEmailActivity::class.java))
        }
    }

    private fun logout() {


        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        Firebase.auth.currentUser?.let {
            Firebase.auth.signOut()
        }
    }


    private val facebookRegisterContract =
        registerForActivityResult(FacebookRegisterContract()) { result ->
            if (result == null) {
                Toast.makeText(this, "teste error", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "${result.providerData[0].displayName}", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private val googleRegisterContract =
        registerForActivityResult(GoogleRegisterContract()) { result ->
            if (result == null) {
                Toast.makeText(this, "teste error", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "${result.providerData[0].displayName}", Toast.LENGTH_LONG)
                    .show()
            }
        }


    override fun onResume() {
        super.onResume()
        logout()
    }
}