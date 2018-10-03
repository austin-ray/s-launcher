object Plugins {
    const val detekt = "io.gitlab.arturbosch.detekt"
}

object Android {
    private val major = 0
    private val minor = 2
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
    const val kotlin = "1.2.70"
    const val kotlinter = "1.17.0"
    const val detekt = "1.0.0.RC8"

    const val androidGradle = "3.2.0"
    const val androidSupport = "28.0.0"
    const val androidConstraint = "1.1.3"
    const val androidLifecycle = "1.1.1"
    const val androidRunner = "1.0.2"

    const val espresso = "3.0.2"
    const val jUnit = "4.12"
}

object Deps {
    // Package groups
    private const val lifecycle = "android.arch.lifecycle"
    private const val support ="com.android.support"
    private const val supportTest = "$support.test"

    // Top-level dependencies
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Vers.kotlin}"
    const val kotlinter = "org.jmailen.gradle:kotlinter-gradle:${Vers.kotlinter}"
    const val androidGradle = "com.android.tools.build:gradle:${Vers.androidGradle}"

    /* App specific dependencies */

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Vers.kotlin}"

    // Support libraries
    const val appCompat = "$support:appcompat-v7:${Vers.androidSupport}"
    const val recyclerView = "$support:recyclerview-v7:${Vers.androidSupport}"
    const val supportV4 = "$support:support-v4:${Vers.androidSupport}"
    const val vectorDrawables = "$support:support-vector-drawable:${Vers.androidSupport}"
    const val constraintLayout = "$support.constraint:constraint-layout:${Vers.androidConstraint}"

    // Lifecycle Dependencies
    val lifeCycleCompiler = "$lifecycle:compiler:${Vers.androidLifecycle}"
    val lifeCycleRuntime = "$lifecycle:runtime:${Vers.androidLifecycle}"
    val lifeCycleViewModel = "$lifecycle:viewmodel:${Vers.androidLifecycle}"
    val lifeCycleLiveData = "$lifecycle:livedata:${Vers.androidLifecycle}"
    val lifeCycleExt = "$lifecycle:extensions:${Vers.androidLifecycle}"

    // Testing dependencies
    val jUnit = "junit:junit:${Vers.jUnit}"
    val testRunner = "$supportTest:runner:${Vers.androidRunner}"
    val espresso = "$supportTest.espresso:espresso-core:${Vers.espresso}"
}
