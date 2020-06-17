package br.com.vineivel.robot.registerlogin.email

import br.com.vineivel.emailregister.R
import br.com.vineivel.robot.base.BaseTestRobot

fun registerLoginEmail(func: RegisterLoginEmailRobot.() -> Unit) = RegisterLoginEmailRobot()
    .apply { func() }

class RegisterLoginEmailRobot : BaseTestRobot() {

    fun clickRegister() {
        clickButton(R.id.btn_register_login_email)
    }

    fun fillFullName(fullname: String) {
        fillEditText(R.id.edt_user_register_full_name, fullname)
    }

    fun fillEmaill(email: String) {
        fillEditText(R.id.edt_user_register_email, email)
    }

    fun fillPassword(password: String) {
        fillEditText(R.id.edt_user_register_password, password)
    }

    fun fillPasswordConfirmation(password: String) {
        fillEditText(R.id.edt_user_register_confirm_password, password)
    }

}