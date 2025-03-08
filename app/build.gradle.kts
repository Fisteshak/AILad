plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // serialization plugin
    alias(libs.plugins.kotlin.serialization)
    // ksp
    id("com.google.devtools.ksp")
    // hilt
    id("com.google.dagger.hilt.android")
    // google services for firebase
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.ailad"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ailad"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    androidResources {
        generateLocaleConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // compose navigation
    implementation(libs.androidx.navigation.compose)
    // serialization lib
    implementation(libs.kotlinx.serialization.json)
    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // hilt-navigation integration
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // gson itself
    implementation("com.google.code.gson:gson:2.11.0")
    // coroutines
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    // integration of hilt with navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // firebase base
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    // datastore
    implementation("androidx.datastore:datastore-preferences:1.1.3")

}


ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}