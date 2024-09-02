import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
}

private val versionName = "0.1.6"

android {
    namespace = "ch.opentransportdata.ojp"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        lint.targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        this.buildConfigField("String", "VERSION_NAME", "\"$versionName\"")

    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            consumerProguardFile("proguard-rules.pro")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        kotlinOptions {
            freeCompilerArgs = listOf("-Xstring-concat=inline")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    tasks.dokkaHtml {
        outputDirectory.set(file("$rootDir/docs/html"))
        moduleName.set("OJP Android SDK")
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)

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
