pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("http://oss.sonatype.org/content/repositories/snapshots")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "OJP Android SDK"
include(":sdk")