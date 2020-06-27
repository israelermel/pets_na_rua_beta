package br.com.vineivel.facebookregister.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.vineivel.domain.model.User
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FacebookRegisterActivity : AppCompatActivity() {

    val userLoggedFirebase by lazy {
        auth.currentUser
    }

    val auth by lazy {
        Firebase.auth
    }

    val callbackManager by lazy {
        CallbackManager.Factory.create()
    }

    val EMAIL = "email"
    val PUBLIC_PROFILE = "public_profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoginManager.getInstance()
            .registerCallback(callbackManager, getFacebookCallback())
    }

    override fun onResume() {
        super.onResume()
        logInFacebook()
    }

    private fun logInFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
            this@FacebookRegisterActivity,
            listOf(EMAIL, PUBLIC_PROFILE)
        )
    }

    private fun getFacebookCallback(): FacebookCallback<LoginResult> {
        return object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                result?.accessToken?.let {
                    handleFacebookAccessToken(it)
                }
            }

            override fun onCancel() {
                setCancelResultFacebook()
                Log.d(TAG, "onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d(TAG, "onError ${error?.message.orEmpty()}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                setResultFacebook()
            } else {
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setResultFacebook() {
        val intent = Intent()
        intent.putExtra("data", getUser())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getUser(): User {
        auth.currentUser?.let { firebaseUser ->
            val userInfo = firebaseUser.providerData[0]
            val providerId = firebaseUser.providerData[1].providerId
            with(userInfo) {
                return User(
                    uid,
                    displayName.orEmpty(),
                    email.orEmpty(),
                    photoUrl.toString(),
                    providerId
                )
            }
        }
        return User()
    }

    private fun setCancelResultFacebook() {
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }


    companion object {
        val TAG = FacebookRegisterActivity::class.java.simpleName
    }
}