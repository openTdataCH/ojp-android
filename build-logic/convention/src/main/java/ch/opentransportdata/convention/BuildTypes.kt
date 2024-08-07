package ch.opentransportdata.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Created by Deniz Kalem on 06.08.2024
 */
internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>, extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType()
                        }
                        release {
                            configureReleaseBuildType(commonExtension)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType()
                        }
                        release {
                            configureReleaseBuildType(commonExtension)
                            consumerProguardFile("proguard-rules.pro")
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType() {
    isMinifyEnabled = false
}

private fun BuildType.configureReleaseBuildType(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    isMinifyEnabled = true
    proguardFiles(commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
}