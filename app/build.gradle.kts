plugins {
    alias(libs.plugins.ojp.android.application.compose)
}

android {
    namespace = "ch.opentransportdata"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":sdk"))

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.navigation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.accompanist.permission)
    implementation(libs.play.services)

    testImplementation(libs.junit)
    debugImplementation(libs.ui.tooling)
}