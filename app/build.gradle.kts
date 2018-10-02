plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("org.jetbrains.kotlin.kapt")

    id("org.jmailen.kotlinter")
}

android {
    compileSdkVersion(Android.targetSdk)
    defaultConfig {
        applicationId = Android.id
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = Version.code
        versionName = Version.name
        testInstrumentationRunner = Android.instrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")

    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    implementation("com.android.support:recyclerview-v7:28.0.0")

    // View model libraries
    implementation("android.arch.lifecycle:runtime:1.1.1")
    implementation("android.arch.lifecycle:viewmodel:1.1.1")
    implementation("android.arch.lifecycle:livedata:1.1.1")
    implementation("android.arch.lifecycle:extensions:1.1.1")
    implementation("com.android.support:support-v4:28.0.0")
    implementation("com.android.support:support-vector-drawable:28.0.0")
    kapt("android.arch.lifecycle:compiler:1.1.1")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}
