plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "ch.opentransportdata.ojp"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        lint.targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        this.buildConfigField("String", "VERSION_NAME", "\"0.0.1\"")

    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.timber)
    testImplementation(libs.junit)
}