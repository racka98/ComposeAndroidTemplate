package com.apimorlabs.composetemplate.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.apimorlabs.composetemplate.extensions.Versions
import com.apimorlabs.composetemplate.extensions.android
import com.apimorlabs.composetemplate.extensions.configureAndroid
import com.apimorlabs.composetemplate.extensions.configureAndroidCompose
import com.apimorlabs.composetemplate.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import kotlin.jvm.optionals.getOrNull

/**
 * This a plugin setting up the application level of compose multiplatform.
 * Will likely be used once per main app module.
 */
class AndroidAppPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            val composeCompiler = libs.findPlugin("compose_compiler").getOrNull()?.orNull
            val androidApp = libs.findPlugin("android_application").getOrNull()?.orNull
            composeCompiler?.let { plugin -> apply(plugin.pluginId) }
            androidApp?.let { plugin -> apply(plugin.pluginId) }
        }

        // Android Setup
        android {
            defaultConfig {
                applicationId = Versions.PACKAGE_NAME
                targetSdk = Versions.TARGET_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        rootProject.file("build-configs/proguard-config/proguard-rules.pro").absolutePath
                    )
                }
            }
        }

        // Configure Android App level
        extensions.configure<ApplicationExtension> {

            configureAndroid()
            configureAndroidCompose(this)

            // Common Dependencies
            dependencies {
                add("implementation", libs.findLibrary("activity_compose").get())
                add("implementation", libs.findLibrary("appCompat").get())
                add("implementation", libs.findLibrary("core_ktx").get())
                add("implementation", libs.findLibrary("google_material").get())
                add("implementation", libs.findLibrary("splash_screen_core").get())
                add("implementation", libs.findLibrary("viewmodel_core").get())
            }
        }
    }
}
