import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
    id("signing")
}

android {
    namespace = "ch.opentransportdata.ojp"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        lint.targetSdk = 35
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "VERSION_NAME", "\"$version\"")
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

publishing {
    publications {
        create<MavenPublication>("releaseOjpSdk") {
            groupId = project.property("GROUP_ID") as String
            artifactId = project.property("ARTIFACT_ID") as String
            version = project.property("VERSION") as String

            afterEvaluate {
                from(components["release"])
            }

            artifact(tasks.named("javadocJar")) {
                builtBy(tasks.named("javadocJar"))
            }

            tasks.named("generateMetadataFileForReleaseOjpSdkPublication").configure {
                dependsOn(tasks.named("javadocJar"))
            }

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
    }
    repositories {
        maven {
            name = "OSSRH"
            url = if (version.toString().endsWith("SNAPSHOT")) {
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            } else {
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
            credentials {
                username = project.findProperty("ossrhUsername") as String? ?: ""
                password = project.findProperty("ossrhPassword") as String? ?: ""
            }
        }
    }
}

signing {
    isRequired = project.hasProperty("signing.keyId")
    sign(publishing.publications["releaseOjpSdk"])
}