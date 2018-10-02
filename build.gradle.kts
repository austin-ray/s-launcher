// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.2.0")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath("org.jmailen.gradle:kotlinter-gradle:1.17.0")
    }
}

plugins {
  id("io.gitlab.arturbosch.detekt") version("1.0.0.RC8")
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task<Delete> ("clean") {
    delete(rootProject.buildDir)
}

detekt {
  version = "1.0.0.RC8"
    profile("Main", Action {
        input = "app/src/main/java"
        filters = ".*/resources/.*,.*/build/.*"
    })
}
