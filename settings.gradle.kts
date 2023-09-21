pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    val hmiRepoPath = extra["atom.hmiRepoPath"] ?: ""
    includeBuild("$hmiRepoPath/manifests/common")
}

val hmiRepoPath = getEnvPropertyByName("HMI_REPO_PATH", extra["atom.hmiRepoPath"] ?: "")
apply {
    from("$hmiRepoPath/manifests/common/substitution.gradle.kts")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("$hmiRepoPath/manifests/common/libs.versions.toml"))
        }
    }
}

fun getEnvPropertyByName(propName: String, defaultValue: Any): Any {
    val result = System.getenv(propName)
    return result ?: defaultValue
}

rootProject.name = "CustomStatusBar"
include(":app")

