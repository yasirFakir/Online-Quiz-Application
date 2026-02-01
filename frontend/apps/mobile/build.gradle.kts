plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.quiz.mobile"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.quiz.mobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":packages:ui"))
    implementation("androidx.activity:activity-compose:1.8.2")
}
