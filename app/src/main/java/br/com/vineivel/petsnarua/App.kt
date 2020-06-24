package br.com.vineivel.petsnarua

import android.app.Application
import br.com.vineivel.data.di.dataModule
import br.com.vineivel.emailregister.di.loginModule
import br.com.vineivel.googleregister.di.googleRegisterModule
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()

        AppCenter.start(
            this,
            "2fa1e53b-6719-4448-8db1-0e10e6630986",
            Analytics::class.java, Crashes::class.java, Distribute::class.java
        )

        Distribute.setEnabled(true)
        Analytics.setEnabled(true)
        Crashes.setEnabled(true)
    }

    private fun initDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    // Platform
                    dataModule,

                    // Libraries

                    // Features
                    loginModule,
                    googleRegisterModule
                )
            )
        }
    }

}