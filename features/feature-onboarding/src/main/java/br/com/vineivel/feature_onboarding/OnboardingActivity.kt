package br.com.vineivel.feature_onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.vineivel.feature_navigation.Actions

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Actions.openFacebookIntent(baseContext))
    }
}
