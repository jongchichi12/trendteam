// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {    // ── Android Gradle Plugin
    id("com.android.application") version "8.13.1" apply false
    id("com.android.library")    version "8.13.1" apply false

    // ✅ Kotlin 1.9.24 조합으로 롤백
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("org.jetbrains.kotlin.kapt")    version "1.9.24" apply false

    // ✅ Hilt
    id("com.google.dagger.hilt.android") version "2.48.1" apply false

}