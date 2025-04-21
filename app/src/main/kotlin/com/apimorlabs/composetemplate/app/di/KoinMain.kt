package com.apimorlabs.composetemplate.app.di

import com.apimorlabs.composetemplate.app.datastore.PrefDataStore
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object KoinMain {
    fun install() = module {
        single {
            PrefDataStore(
                context = androidContext(),
                dispatcher = Dispatchers.IO
            )
        }
    }
}
