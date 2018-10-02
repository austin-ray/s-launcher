object Version {
    private val major = 0
    private val minor = 2
    private val build = 0

    private const val majorIncrement = 100_000
    private const val minorIncrement = 1_000

    val code = major * majorIncrement +  minor * minorIncrement + build
    val name = "$major.$minor.$build"
}

object Android {
    const val id = "io.austinray.slauncher"
    const val minSdk = 19
    const val targetSdk = 28

    const val instrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
}

object Versions {
    val kotlin = "1.2.70"
}
