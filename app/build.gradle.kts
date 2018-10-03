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
        versionCode = Android.code
        versionName = Android.name
        testInstrumentationRunner = Android.instrumentationRunner
        vectorDrawables.useSupportLibrary = Android.supportDrawables
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
    implementation(Deps.kotlinStdLib)

    // Android support libraries
    implementation(Deps.appCompat)
    implementation(Deps.constraintLayout)
    implementation(Deps.recyclerView)
    implementation(Deps.supportV4)
    implementation(Deps.vectorDrawables)

    // Android Lifecycle libraries
    kapt(Deps.lifeCycleCompiler)
    implementation(Deps.lifeCycleRuntime)
    implementation(Deps.lifeCycleViewModel)
    implementation(Deps.lifeCycleLiveData)
    implementation(Deps.lifeCycleExt)


    testImplementation(Deps.jUnit)
    androidTestImplementation(Deps.testRunner)
    androidTestImplementation(Deps.espresso)
}
