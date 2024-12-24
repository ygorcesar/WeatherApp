import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

private fun BaseExtension.setupAndroid() {
    compileSdkVersion(35)
    defaultConfig {
        minSdk = 24
        targetSdk = 34

        versionCode = 1
        versionName = "1.0.0"
    }
}

fun Project.setupAndroidModule() {
    val androidExtension: BaseExtension = extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<com.android.build.gradle.AppExtension>()
        ?: error("Could not found Android application or library plugin applied on module $name")

    kotlinExtension.jvmToolchain(17)

    androidExtension.apply {
        setupAndroid()

        buildFeatures.apply {
            compose = true
            buildConfig = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        testOptions {
            unitTests.isReturnDefaultValues = true
            unitTests.isIncludeAndroidResources = true
        }

        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}