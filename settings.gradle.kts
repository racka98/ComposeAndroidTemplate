rootProject.name = "ComposeAndroidTemplate"
include(":app")

pluginManagement {
    includeBuild("build-configs")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
    }
}
