package br.com.vineivel.registerlogin.email

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import br.com.vineivel.login.di.loginModule
import br.com.vineivel.login.presentation.RegisterLoginEmailActivity
import com.schibsted.spain.barista.rule.BaristaRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

@RunWith(AndroidJUnit4ClassRunner::class)
class RegisterLoginEmailAndroidTest {

    //    private var device: UiDevice =
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    var baristaRule = BaristaRule.create(RegisterLoginEmailActivity::class.java)

    private var context = InstrumentationRegistry.getInstrumentation().context

    init {
        startKoin {}
        loadKoinModules(loginModule)
    }

    @Before
    fun setup() {
        baristaRule.launchActivity()
    }

    @Test
    fun teste() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("br.com.vineivel.login.test", appContext.packageName)
    }

//    // Use a RuleChain to wrap your ActivityTestRule with a FlakyTestRule
//    private val emailActivityRuleRegister: ActivityTestRule<RegisterLoginEmailActivity> =
//        ActivityTestRule(RegisterLoginEmailActivity::class.java)
//
//    private val flakyRule = FlakyTestRule()
//
//    @get:Rule
//    var chain = RuleChain.outerRule(flakyRule).around(emailActivityRuleRegister)
//
//
//    @Before
//    fun setup() {
//
//    }
//
//    @After
//    fun tearDown() {
//        stopKoin()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    @AllowFlaky(attempts = 1)
//    fun teste() {
//        registerLoginEmail {
//
//            given {
//                fillFullName("teste")
//                fillEmaill("israelermel@asldfk.com")
//                fillPassword("1234")
//                fillPasswordConfirmation("1234")
//            }
//
//            whenever {
//                clickRegister()
//            }
//
//            then {
//                BaristaSleepInteractions.sleep(5, TimeUnit.SECONDS)
//            }
//
//        }
//    }

}


