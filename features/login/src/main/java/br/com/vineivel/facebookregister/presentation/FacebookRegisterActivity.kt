package br.com.vineivel.facebookregister.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.vineivel.emailregister.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FacebookRegisterActivity : AppCompatActivity() {

    private lateinit var loginButton: LoginButton
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth

    val EMAIL = "email"
    val PUBLIC_PROFILE = "public_profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_register)

        callbackManager = CallbackManager.Factory.create()

        auth = Firebase.auth

        loginButton = findViewById(R.id.login_button);
        loginButton.setPermissions(listOf(EMAIL, PUBLIC_PROFILE));

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {

                result?.accessToken?.let {
                    handleFacebookAccessToken(it)
                }

            }

            override fun onCancel() {
                Log.d(TAG, "onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d(TAG, "onError ${error?.message.orEmpty()}")
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

//    EAANmw1OkHHcBABaG8Jdjit7A3fHURiEApEY0LVzXPEF6ozO68aJlRtlxgPFgfUUqW2CAV7a7GLvnU2ShnxIT3F28dUHVIj6PJoQFK3VZAZCny51jMfoA2ceQwGiRe2aPcKFlsc4isZC8iTxIiQskikZCn04z6cszh31rlWo74Mf6LF2hbZArkuFWVZAtRGm7Ub3ZCOsjBGYKSTO5vMy5ua0

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)

        Log.d(TAG, credential.provider)
        Log.d(TAG, credential.signInMethod)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }

                // ...
            }
    }

    companion object {
        val TAG = FacebookRegisterActivity::class.java.simpleName
    }
}