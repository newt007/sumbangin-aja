package com.bintangpoetra.sumbanginaja.base

import android.app.Application
import com.bintangpoetra.sumbanginaja.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class SumbanginAjaApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@SumbanginAjaApplication)
            modules(
                listOf(
                    networkModule
                )
            )
        }
    }

}