package br.com.vineivel.emailregister.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import br.com.vineivel.domain.model.User
import br.com.vineivel.emailregister.presentation.RegisterLoginEmailActivity

class EmailRegisterContract : ActivityResultContract<Int, User?>() {

    override fun createIntent(context: Context, input: Int?): Intent {
        val intent = Intent(context, RegisterLoginEmailActivity::class.java)
        intent.putExtra("data", 0)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): User? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getParcelableExtra<User>("data")
    }


}