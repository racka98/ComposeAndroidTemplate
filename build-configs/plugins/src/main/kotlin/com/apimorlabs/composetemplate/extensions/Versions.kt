package com.apimorlabs.composetemplate.extensions

import org.gradle.api.JavaVersion

object Versions {
    const val COMPILE_SDK = 35
    const val MIN_SDK = 26
    const val TARGET_SDK = COMPILE_SDK
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    val PACKAGE_NAME = "com.apimorlabs.composetemplate"
    val DESKTOP_VERSION = "1.0.0"
    val DESKTOP_PACKAGE_NAME = "ComposeAndroidTemplate"
    val jvmTarget: String = JavaVersion.VERSION_17.majorVersion
}
