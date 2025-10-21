// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        // Si estás usando buildscript para otros plugins, añade también:
        // classpath("com.google.dagger:hilt-android-gradle-plugin:2.57.2")

        // 👇 Fuerza javapoet moderno en el classpath de plugins:
        classpath("com.squareup:javapoet:1.13.0")
    }
}