plugins {
    alias(libs.plugins.ojp.android.library)
    alias(libs.plugins.dokka)
    `maven-publish`
}

private val versionName = "0.1.0"

android {
    namespace = "ch.opentransportdata.ojp"

    defaultConfig {
        lint.targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        this.buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
    }

    kotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xstring-concat=inline")
        }
    }

    tasks.dokkaHtml {
        outputDirectory.set(file("$rootDir/docs/html"))
        moduleName.set("OJP Android SDK")
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.timber)
    implementation(libs.koin.android)
    implementation(libs.retrofit)
    implementation(libs.okHttp)
    implementation(libs.okHttpLogger)
    implementation(libs.tikRetrofit)
    implementation(libs.tikAnnotation)
    implementation(libs.tikConverters)
    kapt(libs.tikProcessor) //needed for TypeAdapter creation
    implementation(libs.joda)
    implementation(libs.dokka)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.kotlinx.coroutines.test)
}

publishing {
    publications {
        val sdkGroupId = "com.github.openTdataCH"
        val sdkArtifactId = "ojp-android"

        create<MavenPublication>("debugOjpSdk") {
            groupId = sdkGroupId
            artifactId = sdkArtifactId
            version = versionName
            afterEvaluate {
                from(components["debug"])
            }
        }
        create<MavenPublication>("releaseOjpSdk") {
            groupId = sdkGroupId
            artifactId = sdkArtifactId
            version = versionName
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
