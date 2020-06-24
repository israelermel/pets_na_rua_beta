package br.com.vineivel.googleregister.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import br.com.vineivel.googleregister.presentation.GoogleRegisterActivity
import com.google.firebase.auth.FirebaseUser

class GoogleRegisterContract : ActivityResultContract<Int, FirebaseUser>() {

    override fun createIntent(context: Context, input: Int?): Intent {
        val intent = Intent(context, GoogleRegisterActivity::class.java)
        intent.putExtra("data", 0)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): FirebaseUser? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getParcelableExtra<FirebaseUser>("data")
    }
}