plugins {
    `kotlin-dsl`
}

group = "com.apimorlabs.reluct.plugins"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    // Dependencies setup here to enable detection of corresponding source files for plugins
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)
}

gradlePlugin {
    // IDs for these custom plugins are obtained from libs.versions.toml for ease of use in project
    plugins {
        register("detektPlugin") {
            id = libs.plugins.detekt.setup.get().pluginId
            implementationClass = "com.apimorlabs.composetemplate.plugins.DetektConventionPlugin"
        }
        register("androidAppPlugin") {
            id = libs.plugins.android.app.get().pluginId
            implementationClass = "com.apimorlabs.composetemplate.plugins.AndroidAppPlugin"
        }
        register("androidLibPlugin") {
            id = libs.plugins.android.lib.get().pluginId
            implementationClass = "com.apimorlabs.composetemplate.plugins.AndroidLibPlugin"
        }
    }
}
