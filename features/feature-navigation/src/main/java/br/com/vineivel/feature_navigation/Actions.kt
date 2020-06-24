package br.com.vineivel.feature_navigation

import android.content.Context
import android.content.Intent

object Actions {
    private fun internalIntentClearTask(context: Context, action: String) =
        Intent(action).setPackage(context.packageName).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

    private fun internalIntent(context: Context, classK: Class<*>) =
        Intent(context, classK).setPackage(context.packageName)


//    fun openDashboardIntent(userId: String) =
//        Intent(context, "action.dashboard.open")
//            .putExtra(EXTRA_USER, UserArgs(userId))

//    activity.startActivity(Actions.openDashboardIntent("userId"))
}