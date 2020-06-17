package br.com.vineivel.emailregister

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import br.com.vineivel.emailregister.di.loginModule
import br.com.vineivel.emailregister.presentation.RegisterLoginEmailActivity
import com.schibsted.spain.barista.rule.BaristaRule
import com.schibsted.spain.barista.rule.flaky.FlakyTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class ExampleInstrumentedTest {

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

//    @Test
//    @AllowFlaky(attempts = 1)
//    @Throws(Exception::class)
//    fun teste() {
//        unicred {
//            given {
//                writeTo(R.id.edt_user_name, "israelermel")
//                writeTo(R.id.edt_password, "123")
//            }
//
//            whenever {
//                device.wait(
//                    Until.findObject(By.res(device.launcherPackageName, "btn_login")),
//                    6000
//                )
//
//                clickOn(R.id.btn_login)
//
//            }
//
//            then {
//                device.waitForWindowUpdate(
//                    device.currentPackageName, 1200
//                )
//
////                clickDialogNegativeButton()
//                clickDialogPositiveButton()
//            }
//        }
//    }


}
