plugins {
    `kotlin-dsl`
}

group = "ch.opentransportdata.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "ojp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "ojp.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "ojp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidUi") {
            id = "ojp.android.ui"
            implementationClass = "AndroidUiConventionPlugin"
        }
    }
}