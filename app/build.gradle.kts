
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.coffeepoints"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.coffeepoints"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":dependencies:di"))
    implementation(project(":dependencies:ui"))
    implementation(project(":dependencies:common"))
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":features:authorization"))
    implementation(project(":features:coffeePointsList"))

    implementation(libs.navigation.compose)

    implementation(libs.gson)

    implementation(libs.play.services.location)

    implementation(libs.accompanist.permissions)

    ksp(libs.dagger.compiler)
}