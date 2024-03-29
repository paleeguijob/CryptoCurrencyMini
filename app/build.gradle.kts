plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kspCompiler)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.serialization)
}

android {
    namespace = "realaof.realhon.realha.cryptocurrencymini"
    compileSdk = 34

    defaultConfig {
        applicationId = "realaof.realhon.realha.cryptocurrencymini"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    testOptions{
//        unitTests.isReturnDefaultValues = true
//    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    //build variant
//    buildTypes {
//        getByName("release") {
//            // Enables code shrinking, obfuscation, and optimization for only
//            // your project's release build type. Make sure to use a build
//            // variant with `isDebuggable=false`.
//            isMinifyEnabled = true
//
//            // Enables resource shrinking, which is performed by the
//            // Android Gradle plugin.
//            isShrinkResources = true
//
//            // Includes the default ProGuard rules files that are packaged with
//            // the Android Gradle plugin. To learn more, go to the section about
//            // R8 configuration files.
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.materail)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.windowsizeclass)
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Dagger Hilt
    implementation(libs.android.hilt)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.android.hilt.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)

    //Icons
    implementation(libs.icon.extended)

    //ViewModel compose
    implementation(libs.androidx.viewmodel.compose)

    //Coil AsyncImageUrl
    implementation(libs.coil.kt)
    implementation(libs.coil.svg)

    //Compose Navigation
    implementation(libs.androidx.navigation.compose)

    //Navigation Testing
    testImplementation(libs.navigation.testing)
    androidTestImplementation(libs.navigation.testing)

    // Compose testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Serialization json
    implementation(libs.kotlinx.serialization.json)

    //GridLayout
    implementation(libs.grid)

    //Testing
    testImplementation(libs.junit)
    testImplementation(libs.junit5)
    testImplementation (libs.mockk)
    testImplementation(libs.arch.core.test)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.hamcrest.test)
    testImplementation(libs.androidx.core.ktx.test)
    testImplementation(libs.app.cash.turbine)
    implementation(libs.androidx.junit.ktx)
}