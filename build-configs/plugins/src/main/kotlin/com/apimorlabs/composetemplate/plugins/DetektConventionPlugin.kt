package com.apimorlabs.composetemplate.plugins

import com.apimorlabs.composetemplate.extensions.Versions
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import com.apimorlabs.composetemplate.extensions.libs
import kotlin.jvm.optionals.getOrNull

/**
 * This is a convention plugin for Detekt
 * To run detekt simply:
 * 1. ./gradlew module:detekt for each module
 * 2. ./gradlew detekt for whole project
 */
class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            val detekt = libs.findPlugin("detektGradle").getOrNull()?.orNull
            detekt?.let { plugin -> apply(plugin.pluginId) }
        }

        val detektPlugin = libs.findPlugin("detektGradle").getOrNull()?.orNull

        detektPlugin?.run {
            apply(plugin = pluginId)

            allprojects {
                afterEvaluate {
                    dependencies {
                        "detektPlugins"(libs.findLibrary("detekt_formatting").get())
                        "detektPlugins"(libs.findLibrary("detekt_rule_twitter_compose").get())
                    }

                    val detekt = extensions.getByType<DetektExtension>()
                    with(detekt) {
                        buildUponDefaultConfig = true
                        config.from("$rootDir/tooling/checks/detekt.yml")
                        baseline = file("$rootDir/tooling/checks//baseline.xml")
                        buildUponDefaultConfig = true
                        ignoredBuildTypes = listOf("release")
                        source.from(
                            DetektExtension.DEFAULT_SRC_DIR_JAVA,
                            DetektExtension.DEFAULT_TEST_SRC_DIR_JAVA,
                            DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
                            DetektExtension.DEFAULT_TEST_SRC_DIR_KOTLIN,
                            // Kotlin Multiplatform
                            "src/commonMain/kotlin",
                            "src/androidMain/kotlin",
                            "src/iosMain/kotlin",
                            "src/jvmMain/kotlin",
                            "src/desktopMain/kotlin",
                            "src/jsMain/kotlin",
                        )
                    }

                    //
                    tasks.withType<Detekt>().configureEach {
                        jvmTarget = Versions.jvmTarget
                        exclude("**/build/**", "**/generated/**", "**/resources/**")
                        basePath = rootProject.projectDir.absolutePath
                        autoCorrect = true // Auto corrects common formatting issues
                        // Configure reports here
                        reports {
                            xml.required.set(false)
                            txt.required.set(false)
                            md.required.set(false)

                            html {
                                required.set(true)
                                outputLocation.set(
                                    layout.buildDirectory.file("reports/detekt.html")
                                )
                            }

                            sarif.required.set(true)
                        }
                    }

                    // Report Merge Task
                    tasks.withType<ReportMergeTask>().configureEach {
                        output.set(layout.projectDirectory.file("reports/detekt/merged_report.sarif"))
                        input.from(tasks.withType<Detekt>().map { it.reports.sarif.outputLocation })
                    }

                    // Create Baseline Task
                    tasks.withType<DetektCreateBaselineTask>().configureEach {
                        jvmTarget = Versions.jvmTarget
                        description = "Overrides current baseline. Used project wide."
                        buildUponDefaultConfig.set(true)
                        ignoreFailures.set(true)
                        parallel.set(true)
                        setSource(files(rootDir))
                        config.setFrom(files("$rootDir/tooling/checks/detekt.yml"))
                        baseline.set(file("$rootDir/tooling/checks/baseline.xml"))
                        include("**/*.kt")
                        include("**/*.kts")
                        exclude("**/build/**", "**/generated/**", "**/resources/**")
                    }
                }
            }
        }
    }
}