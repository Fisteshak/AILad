// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // hilt
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    // ksp
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    // firebase
    id("com.google.gms.google-services") version "4.4.2" apply false

}