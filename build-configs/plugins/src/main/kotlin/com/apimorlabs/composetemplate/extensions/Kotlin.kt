package com.apimorlabs.composetemplate.extensions

import com.android.build.gradle.tasks.asJavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure

internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaLanguageVersion.of(Versions.jvmTarget).asJavaVersion()
        targetCompatibility = JavaLanguageVersion.of(Versions.jvmTarget).asJavaVersion()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(Versions.jvmTarget))
        }
    }
}

private fun Project.java(action: JavaPluginExtension.() -> Unit) =
    extensions.configure<JavaPluginExtension>(action)
