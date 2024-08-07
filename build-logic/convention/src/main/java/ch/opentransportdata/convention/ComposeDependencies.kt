package ch.opentransportdata.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * Created by Deniz Kalem on 06.08.2024
 */
fun DependencyHandlerScope.addUiLayerDependencies(project: Project) {
    "implementation"(project.libs.findBundle("compose").get())
    "debugImplementation"(project.libs.findBundle("compose-debug").get())
}