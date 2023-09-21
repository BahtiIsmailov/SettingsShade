import com.android.build.gradle.AppExtension
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
val hmiRepoPath = extra["atom.hmiRepoPath"] ?: ""

//For platform signing purposes
subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("com.android.application")) {
            project.extensions.configure(AppExtension::class) {
                val config = signingConfigs.maybeCreate("debug")
                config.apply {
                    //replace platform.jks with aosp.jsk if you want to launch app on plain AOSP
                    storeFile = file("../${hmiRepoPath}/manifests/common/keystore/platform.jks")
                    storePassword = "android"
                    keyAlias = "android"
                    keyPassword = "android"
                }
                buildTypes.configureEach {
                    signingConfig = config
                    project.logger.info("Applied config for $this")
                }
            }
        }
    }
}
