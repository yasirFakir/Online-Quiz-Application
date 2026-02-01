rootProject.name = "quiz-frontend"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.software/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.software/public/p/compose/dev")
    }
}

include(":desktop")
include(":mobile")
include(":packages:ui")
include(":packages:core")

project(":desktop").projectDir = file("apps/desktop")
project(":mobile").projectDir = file("apps/mobile")
project(":packages:ui").projectDir = file("packages/ui")
project(":packages:core").projectDir = file("packages/core")