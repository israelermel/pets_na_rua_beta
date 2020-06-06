package br.com.vineivel.registerlogin.email

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import br.com.vineivel.login.di.loginModule
import br.com.vineivel.login.presentation.RegisterLoginEmailActivity
import br.com.vineivel.robot.base.given
import br.com.vineivel.robot.base.then
import br.com.vineivel.robot.base.whenever
import br.com.vineivel.robot.registerlogin.email.registerLoginEmail
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import com.schibsted.spain.barista.rule.BaristaRule
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import com.schibsted.spain.barista.rule.flaky.FlakyTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4ClassRunner::class)
class RegisterLoginEmailAndroidTest {

    init {
        startKoin {}
        loadKoinModules(loginModule)
    }

    private var context = InstrumentationRegistry.getInstrumentation().context
    private var device: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    var baristaRule = BaristaRule.create(RegisterLoginEmailActivity::class.java)

    // Use a RuleChain to wrap your ActivityTestRule with a FlakyTestRule
    private val emailActivityRuleRegister: ActivityTestRule<RegisterLoginEmailActivity> =
        ActivityTestRule(RegisterLoginEmailActivity::class.java)

    private val flakyRule = FlakyTestRule()

    @get:Rule
    var chain = RuleChain.outerRule(flakyRule).around(emailActivityRuleRegister)


    @Before
    fun setup() {

    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    @Throws(Exception::class)
    @AllowFlaky(attempts = 1)
    fun teste() {
        registerLoginEmail {

            given {
                fillFullName("teste")
                fillEmaill("israelermel@asldfk.com")
                fillPassword("1234")
                fillPasswordConfirmation("1234")
            }

            whenever {
                clickRegister()
            }

            then {
                BaristaSleepInteractions.sleep(5, TimeUnit.SECONDS)
            }

        }
    }

}