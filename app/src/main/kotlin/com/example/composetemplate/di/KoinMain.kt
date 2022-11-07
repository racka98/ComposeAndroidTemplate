package com.example.composetemplate.di

import com.example.composetemplate.data.local.datastore.PrefDataStore
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
