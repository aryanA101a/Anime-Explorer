plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}

buildscript {
    val agp_version by extra("8.1.2")
    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48.1")
    }
}


