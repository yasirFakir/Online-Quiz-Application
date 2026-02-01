plugins {
    kotlin("multiplatform") version "1.9.22" apply false
    id("org.jetbrains.compose") version "1.5.12" apply false
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.software/public/p/compose/dev")
    }
}