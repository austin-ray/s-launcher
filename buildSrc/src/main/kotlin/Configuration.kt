object Plugins {
    const val detekt = "io.gitlab.arturbosch.detekt"
}

object Android {
    private val major = 0
    private val minor = 3
    private val build = 0

    private const val majorIncrement = 100_000
    private const val minorIncrement = 1_000

    val code = major * majorIncrement +  minor * minorIncrement + build
    val name = "$major.$minor.$build"

    const val id = "io.austinray.slauncher"
    const val minSdk = 19
    const val targetSdk = 28

    const val instrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    const val supportDrawables = true
}

object Vers {
    const val kotlin = "1.3.11"
    const val kotlinter = "1.20.1"
    const val detekt = "1.0.0-RC12"

    const val androidGradle = "3.2.1"
    const val androidSupport = "28.0.0"
    const val androidConstraint = "1.1.3"
    const val androidXSupport = "1.0.0"
    const val androidXLifecycle = "2.0.0"
    const val androidRunner = "1.0.2"

    const val colorPicker = "1.1.0"

    const val espresso = "3.1.0"
    const val jUnit = "4.12"
}

object Deps {
    // Package groups
    private const val lifecycle = "androidx.lifecycle"
    private const val androidX ="androidx"
    private const val androidXTest = "$androidX.test"

    // Top-level Gradle dependencies
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Vers.kotlin}"
    const val kotlinter = "org.jmailen.gradle:kotlinter-gradle:${Vers.kotlinter}"
    const val androidGradle = "com.android.tools.build:gradle:${Vers.androidGradle}"

    /* Submodule dependencies */
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Vers.kotlin}"

    // Color picker
    const val colorPicker = "com.jaredrummler:colorpicker:${Vers.colorPicker}"

    // Support libraries
    const val appCompat = "$androidX.appcompat:appcompat:${Vers.androidXSupport}"
    const val recyclerView = "$androidX.recyclerview:recyclerview:${Vers.androidXSupport}"
    const val supportV4 = "$androidX.legacy:legacy-support-v4:${Vers.androidXSupport}"
    const val vectorDrawables = "$androidX.vectordrawable:vectordrawable:${Vers.androidXSupport}"
    const val constraintLayout = "$androidX.constraintlayout:constraintlayout:${Vers.androidConstraint}"
    const val preferences = "$androidX.preference:preference:${Vers.androidXSupport}"
    const val preferencesLegacy = "$androidX.legacy:legacy-preference-v14:${Vers.androidXSupport}"

    // Lifecycle Dependencies
    const val lifeCycleCompiler = "$lifecycle:lifecycle-compiler:${Vers.androidXLifecycle}"
    const val lifeCycleRuntime = "$lifecycle:lifecycle-runtime:${Vers.androidXLifecycle}"
    const val lifeCycleViewModel = "$lifecycle:lifecycle-viewmodel-ktx:${Vers.androidXLifecycle}"
    const val lifeCycleLiveData = "$lifecycle:lifecycle-livedata:${Vers.androidXLifecycle}"
    const val lifeCycleExt = "$lifecycle:lifecycle-extensions:${Vers.androidXLifecycle}"

    // Testing dependencies
    const val jUnit = "junit:junit:${Vers.jUnit}"
    const val testRunner = "$androidXTest:runner:${Vers.androidRunner}"
    const val espresso = "$androidXTest.espresso:espresso-core:${Vers.espresso}"
}
