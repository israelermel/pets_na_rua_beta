package br.com.vineivel.petsnarua

import android.app.Application
import br.com.vineivel.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    dataModule
                )
            )
        }
    }

}