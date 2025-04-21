package com.apimorlabs.composetemplate.plugins


import com.android.build.api.dsl.LibraryExtension
import com.apimorlabs.composetemplate.extensions.Versions
import com.apimorlabs.composetemplate.extensions.configureAndroid
import com.apimorlabs.composetemplate.extensions.configureAndroidCompose
import com.apimorlabs.composetemplate.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import kotlin.jvm.optionals.getOrNull

/**
 * This a plugin setting up the library level of Android app.
 * To be applied in all library modules that would also contain compose code
 */
class AndroidLibPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            val composeCompiler = libs.findPlugin("compose-compiler").getOrNull()?.orNull
            val androidApp = libs.findPlugin("androidLibrary").getOrNull()?.orNull
            composeCompiler?.let { plugin -> apply(plugin.pluginId) }
            androidApp?.let { plugin -> apply(plugin.pluginId) }
        }

        // Android Setup
        // Common Dependencies
        dependencies {
            add("implementation", libs.findLibrary("activity_compose").get())
            add("implementation", libs.findLibrary("appCompat").get())
            add("implementation", libs.findLibrary("core_ktx").get())
            add("implementation", libs.findLibrary("google_material").get())
            add("implementation", libs.findLibrary("splash_screen_core").get())
            add("implementation", libs.findLibrary("viewmodel_core").get())
        }

        // Configure Android Library
        extensions.configure<LibraryExtension> {
            defaultConfig {
                testOptions.targetSdk = Versions.TARGET_SDK
            }


            configureAndroid()
            configureAndroidCompose(this)
        }

    }
}
