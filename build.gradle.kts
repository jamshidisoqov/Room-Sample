// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false
}

configurations {
    all {
        exclude(group = "org.jetbrains", module = "annotations")
    }
}


subprojects {
    plugins.matching { it is com.android.build.gradle.AppPlugin || it is com.android.build.gradle.LibraryPlugin }.whenPluginAdded {
        configure<com.android.build.gradle.BaseExtension> {
            packagingOptions {
                kotlin.with(excludes) {
                    add("META-INF/ASL2.0")
                    add("META-INF/gradle/incremental.annotation.processors")
                }
            }

        }
    }
}