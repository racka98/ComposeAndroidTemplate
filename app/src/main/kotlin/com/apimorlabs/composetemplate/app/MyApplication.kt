package com.apimorlabs.composetemplate.app

import android.app.Application
import com.apimorlabs.composetemplate.app.di.KoinMain
import com.squareup.leakcanary.core.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(KoinMain.install())
        }

        Timber.plant(Timber.DebugTree())
    }
}
