import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.detekt.gradle)
}

buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://www.jitpack.io")
    }
    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.majorVersion
    }
}

subprojects {

    // Detekt
    apply(plugin = "io.gitlab.arturbosch.detekt")

    /**
     * Start - Detekt Configuration for All sub projects
     */
    detekt {
        config = files("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        ignoredBuildTypes = listOf("release")
        source = files(
            io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_SRC_DIR_JAVA,
            io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_TEST_SRC_DIR_JAVA,
            io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
            io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_TEST_SRC_DIR_KOTLIN,
            // Kotlin Multiplatform
            "src/commonMain/kotlin",
            "src/androidMain/kotlin",
            "src/iosMain/kotlin",
            "src/jvmMain/kotlin",
            "src/desktopMain/kotlin",
            "src/jsMain/kotlin",
        )
    }

    tasks.withType<Detekt>().configureEach detekt@{
        exclude("**/build/**", "**/generated/**", "**/resources/**")
        basePath = rootProject.projectDir.absolutePath
        autoCorrect = true
        reports {
            xml.required.set(false)
            html.required.set(true)
            txt.required.set(false)
            sarif.required.set(false)
            md.required.set(false)

            html {
                required.set(true)
                outputLocation.set(
                    this@subprojects.layout.buildDirectory.file("reports/detekt.html")
                )
            }
        }
    }

    tasks.withType<DetektCreateBaselineTask>().configureEach detekt@{
        exclude("**/build/**", "**/generated/**", "**/resources/**")
        basePath = rootProject.projectDir.absolutePath
    }

    beforeEvaluate {
        dependencies {
            detektPlugins(libs.detekt.formatting)
            detektPlugins(libs.detekt.rule.twitter.compose)
        }

        // Filter out Detekt from check task
        if (tasks.names.contains("check")) {
            tasks.named("check").configure {
                this.setDependsOn(this.dependsOn.filterNot {
                    it is TaskProvider<*> && it.name == "detekt"
                })
            }
        }
    }
}
