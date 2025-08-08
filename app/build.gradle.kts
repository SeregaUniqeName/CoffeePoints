import java.io.FileInputStream
import java.util.Properties

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
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localPropertiesFile: File = rootProject.file("local.properties")
        val localProperties = Properties()
        localProperties.load(FileInputStream(localPropertiesFile))

        buildConfigField("String", "API_KEY", localProperties["yandexMapApiKey"].toString())

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
        buildConfig = true
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
    implementation(project(":features:coffeePointsMap"))

    implementation(libs.navigation.compose)

    implementation(libs.gson)

    implementation(libs.play.services.location)

    implementation(libs.accompanist.permissions)

    implementation(libs.yandex.maps)

    ksp(libs.dagger.compiler)
}