import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
    id("com.vanniktech.maven.publish") version "0.36.0"
    id("signing")
}

android {
    namespace = "ch.opentransportdata.ojp"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        lint.targetSdk = 36
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        val localPropsFile = project.rootProject.file("local.properties")

        if (localPropsFile.exists()) {
            properties.load(localPropsFile.reader())
        }

        val apiKey = properties.getProperty("apiKey")
            ?: System.getProperty("API_KEY")
            ?: System.getenv("API_KEY")

        if (apiKey.isNullOrBlank()) {
            throw GradleException("API_KEY is not set. Provide it in local.properties, system property, or as environment variable.")
        }

        this.buildConfigField("String", "VERSION_NAME", "\"$version\"")
        this.buildConfigField("String", "API_KEY", "\"$apiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.add("-Xstring-concat=inline")
        }
    }

    buildFeatures {
        buildConfig = true
    }

}

dokka {
    moduleName.set("OJP-Android-SDK")
    dokkaPublications.html {
        outputDirectory.set(file("$rootDir/docs/html"))
    }
}

tasks.dokkaGeneratePublicationHtml {
    dependsOn("kaptReleaseKotlin")
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.timber)
    implementation(libs.koin.android)
    implementation(libs.retrofit)
    implementation(libs.okHttp)
    implementation(libs.okHttpLogger)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.dokka)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.xml.test)
    implementation(libs.xmlUtilCore)
    implementation(libs.xmlUtilSerialization)
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.dokkaGeneratePublicationHtml.flatMap { it.outputDirectory })
}

mavenPublishing {
    configure(
        AndroidSingleVariantLibrary(
            variant = "release",
            sourcesJar = SourcesJar.Sources(),
            javadocJar = JavadocJar.Dokka("dokkaGeneratePublicationHtml"),
        )
    )
    publishToMavenCentral()
    signAllPublications()
    coordinates(
        project.property("GROUP_ID") as String,
        project.property("ARTIFACT_ID") as String,
        project.property("VERSION") as String
    )
    pom {
        name.set(project.property("POM_NAME") as String)
        description.set(project.property("POM_DESCRIPTION") as String)
        url.set(project.property("POM_URL") as String)

        licenses {
            license {
                name.set(project.property("POM_LICENSE_NAME") as String)
                url.set(project.property("POM_LICENSE_URL") as String)
            }
        }

        developers {
            developer {
                id.set(project.property("POM_DEVELOPER_ID") as String)
                name.set(project.property("POM_DEVELOPER_NAME") as String)
                email.set(project.property("POM_DEVELOPER_EMAIL") as String)
            }
        }

        scm {
            connection.set(project.property("POM_SCM_CONNECTION") as String)
            developerConnection.set(project.property("POM_SCM_DEV_CONNECTION") as String)
            url.set(project.property("POM_URL") as String)
        }
    }
}