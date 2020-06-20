package br.com.vineivel.feature_onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.vineivel.mainlogin.presentation.MainLoginActivity

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainLoginActivity::class.java)
        startActivity(intent)
    }
}
