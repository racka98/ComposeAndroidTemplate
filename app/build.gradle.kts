import com.apimorlabs.composetemplate.extensions.Versions

plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin)
}

android {
    namespace = Versions.PACKAGE_NAME + ".app"
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // Core Functionality
    implementation(libs.androidx.profileinstaller)
    implementation(libs.core.ktx)

    // Testing
    testImplementation(libs.junit.core)
    testImplementation(libs.junit.test)
    testImplementation(libs.junit.test.ktx)
    androidTestImplementation(libs.junit.test)

    testImplementation(libs.androidx.test.arch.core)
    androidTestImplementation(libs.androidx.test.arch.core)
    androidTestImplementation(libs.androidx.test.core)

    // Compose
    implementation(platform(libs.compose.bom))
    // This is bundle for all common Compose deps
    implementation(libs.bundles.compose.core)
    implementation(libs.lifecycle.compose.runtime)

    // Testing Compose
    //androidTestImplementation(libs.compose.junit)
    debugImplementation(libs.compose.tooling)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // Koin DI
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    // Splash Screen
    implementation(libs.splash.screen.core)
    // Timber - Logging
    implementation(libs.timber.log)

    // Memory Leaks
    debugImplementation(libs.leakcanary.android)
}
