import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost
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
    id("com.vanniktech.maven.publish") version "0.31.0"
    id("signing")
}

android {
    namespace = "ch.opentransportdata.ojp"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        lint.targetSdk = 35
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        val localPropsFile = project.rootProject.file("local.properties")

        if (localPropsFile.exists()) {
            properties.load(localPropsFile.reader())
        }

        val apiKey = properties.getProperty("apiKey") ?: System.getenv("API_KEY")

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
        moduleName.set("OJP-Android-SDK")
        dependsOn("kaptReleaseKotlin")
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
    kapt(libs.tikProcessor) // needed for TypeAdapter creation
    implementation(libs.dokka)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.kotlinx.coroutines.test)
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml.get().outputDirectory)
}

mavenPublishing {
    configure(
        AndroidSingleVariantLibrary(
            variant = "release",
            sourcesJar = true,
            publishJavadocJar = true,
        )
    )
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
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
