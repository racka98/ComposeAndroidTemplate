package com.example.composetemplate.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.composetemplate.data.local.datastore.PrefDataStore
import com.example.composetemplate.ui.components.Greeting
import com.example.composetemplate.ui.theme.ComposeAndroidTemplateTheme
import com.example.composetemplate.ui.theme.Theme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable support for Splash Screen API for
        // proper Android 12+ support
        installSplashScreen()

        val preferences by inject<PrefDataStore>()
        setContent {
            val themeValue by preferences.readThemeSetting
                .collectAsState(initial = Theme.MATERIAL_YOU.themeValue)

            ComposeAndroidTemplateTheme(theme = themeValue) {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Greeting("Compose")
                }
            }
        }
    }
}
