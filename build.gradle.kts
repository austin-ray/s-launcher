// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Deps.androidGradle)
        classpath(Deps.kotlin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath(Deps.kotlinter)
    }
}

plugins {
  id(Plugins.detekt) version(Vers.detekt)
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
  version = Vers.detekt
    profile("Main", Action {
        input = "app/src/main/java"
        filters = ".*/resources/.*,.*/build/.*"
    })
}
