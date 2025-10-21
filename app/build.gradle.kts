plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

android {
    namespace = "com.baubap.challenge"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.baubap.challenge"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.orbit.core)
    implementation(libs.orbit.viewmodel)
    implementation(libs.orbit.mvi)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)
    implementation(libs.retrofit.converters)
    implementation(libs.serialization)
    implementation("com.google.dagger:hilt-android:2.57.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.squareup:javapoet:1.13.0")
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")
    kapt("com.google.dagger:hilt-android-compiler:2.57.2")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "com.squareup" && requested.name == "javapoet") {
            useVersion("1.13.0")
            because("Hilt/aggregator requiere ClassName.canonicalName()")
        }
        // Asegura metadata compatible con Kotlin 2.2.x
        if (requested.group == "org.jetbrains.kotlinx" && requested.name == "kotlinx-metadata-jvm") {
            useVersion("0.9.0")
        }
    }
}
