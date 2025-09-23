pluginManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        flatDir { dirs("${rootDir}/libs") }
        google()
        mavenCentral()
        mavenLocal()
        maven {
            setUrl("http://oss.sonatype.org/content/repositories/snapshots")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "OJP Android SDK"
include(":sdk")
include(":app")
