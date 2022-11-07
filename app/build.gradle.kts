
plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.composetemplate"

    compileSdk = libs.versions.config.android.compilesdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.config.android.applicationId.get()
        minSdk = libs.versions.config.android.minsdk.get().toInt()
        targetSdk = libs.versions.config.android.targetsdk.get().toInt()
        versionCode = libs.versions.config.android.versioncode.get().toInt()
        versionName = libs.versions.config.android.versionName.get()

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
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
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
    // Desugaring
    coreLibraryDesugaring(libs.desugaring)

    // Core Functionality
    implementation(libs.core.ktx)
    implementation(libs.appCompat)
    implementation(libs.google.material)
    implementation(libs.viewmodel.core)
    implementation(libs.androidx.profileinstaller)

    // Testing
    testImplementation(libs.junit.core)
    testImplementation(libs.junit.test)
    testImplementation(libs.junit.test.ktx)
    androidTestImplementation(libs.junit.test)

    testImplementation(libs.androidx.test.arch.core)
    androidTestImplementation(libs.androidx.test.arch.core)
    androidTestImplementation(libs.androidx.test.core)

    // Compose
    // This is bundle for all common Compose deps
    implementation(libs.bundles.compose.core)
    implementation(libs.lifecycle.compose.runtime)

    // Testing Compose
    androidTestImplementation(libs.compose.junit)
    debugImplementation(libs.compose.tooling)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // Koin DI
    implementation(libs.koin.android)
    // Splash Screen
    implementation(libs.splash.screen.core)
    // Timber - Logging
    implementation(libs.timber.log)

    // Memory Leaks
    debugImplementation(libs.leakcanary.android)
}
