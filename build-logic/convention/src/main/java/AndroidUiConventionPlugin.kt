import ch.opentransportdata.convention.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by Deniz Kalem on 06.08.2024
 */
class AndroidUiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("ojp.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}