package com.apimorlabs.composetemplate.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {

        compileSdk = Versions.COMPILE_SDK

        defaultConfig {
            minSdk = Versions.MIN_SDK
        }

        buildFeatures {
            compose = true
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
            }
        }

        compileOptions.isCoreLibraryDesugaringEnabled = true

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }

        configureComposeCompiler()
        configureKotlinJvm()

        dependencies {
            add("coreLibraryDesugaring", libs.findLibrary("desugaring").get())
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                freeCompilerArgs.addAll(
                    listOf(
                        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                        "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                        "-opt-in=com.github.takahirom.roborazzi.ExperimentalRoborazziApi",
                        "-opt-in=dev.chrisbanes.snapper.ExperimentalSnapperApi",
                        "-opt-in=kotlin.RequiresOptIn",
                    )
                )
            }
        }
    }
}

private fun Project.configureComposeCompiler() {
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        // Needed for Layout Inspector to be able to see all of the nodes in the component tree:
        //https://issuetracker.google.com/issues/338842143
        includeSourceInformation.set(true)
    }
}
