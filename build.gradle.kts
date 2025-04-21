// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.detekt.gradle) apply false
    alias(libs.plugins.detekt.setup) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin) apply false
}

allprojects {


    afterEvaluate {
        apply(plugin = libs.plugins.detekt.setup.get().pluginId)

        // Remove log pollution until Android support in KMP improves.
        project.extensions
            .findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()
            ?.let { kmpExt ->
                kmpExt.sourceSets.removeAll {
                    setOf(
                        "androidAndroidTestRelease",
                        "androidTestFixtures",
                        "androidTestFixturesDebug",
                        "androidTestFixturesRelease",
                        "androidTestFixturesDemo"
                    )
                        .contains(it.name)
                }
            }
    }
}
