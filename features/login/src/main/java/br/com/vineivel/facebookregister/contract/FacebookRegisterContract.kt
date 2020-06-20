package br.com.vineivel.facebookregister.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import br.com.vineivel.facebookregister.presentation.FacebookRegisterActivity
import com.google.firebase.auth.FirebaseUser

class FacebookRegisterContract : ActivityResultContract<Int, FirebaseUser?>() {

    override fun createIntent(context: Context, input: Int?): Intent {
        val intent = Intent(context, FacebookRegisterActivity::class.java)
        intent.putExtra("data", 0)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): FirebaseUser? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getParcelableExtra<FirebaseUser>("data")
    }

}