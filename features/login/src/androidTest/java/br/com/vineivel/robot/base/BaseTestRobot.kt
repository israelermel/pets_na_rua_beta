package br.com.vineivel.robot.base

import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.longClickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo

inline fun given(block: () -> Unit) = block()

inline fun whenever(block: () -> Unit) = block()

inline fun then(block: () -> Unit) = block()

abstract class BaseTestRobot {
    protected fun fillEditText(resId: Int, text: String) {
        writeTo(resId, text)
    }

    protected fun clickButton(resId: Int) {
        clickOn(resId)
    }

    protected fun longClickButton(resId: Int) {
        longClickOn(resId)
    }

}