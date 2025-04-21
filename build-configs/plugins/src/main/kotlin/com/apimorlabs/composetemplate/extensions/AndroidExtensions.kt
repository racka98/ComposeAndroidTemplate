package com.apimorlabs.composetemplate.extensions

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid() {
    android {
        compileSdkVersion = "android-${Versions.COMPILE_SDK}"

        defaultConfig {
            minSdk = Versions.MIN_SDK
            targetSdk = Versions.TARGET_SDK
            versionCode = Versions.VERSION_CODE
            versionName = Versions.VERSION_NAME
        }

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
        }

        buildFeatures.buildConfig = true

        packagingOptions {
            resources {
                excludes += mutableSetOf(
                    "/META-INF/{AL2.0,LGPL2.1}",
                    "META-INF/licenses/ASM"
                )
                // Fixes conflicts caused by ByteBuddy library used in
                // coroutines-debug and mockito
                pickFirsts += mutableSetOf(
                    "win32-x86-64/attach_hotspot_windows.dll",
                    "win32-x86/attach_hotspot_windows.dll"
                )
            }
        }
    }

    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("desugaring").get())
    }
}

internal fun Project.android(action: BaseExtension.() -> Unit) =
    (extensions.findByName("android") as? BaseExtension)?.apply {
        configure(action)
    }
