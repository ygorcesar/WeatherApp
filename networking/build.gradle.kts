plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.gradle)
}

setupAndroidModule()

android {
    namespace = "com.challenge.networking"

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"http://api.weatherapi.com/\"")
            buildConfigField("String", "API_KEY", "\"610f2c62b4d9446da32220625242012\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"http://api.weatherapi.com/\"")
            buildConfigField("String", "API_KEY", "\"610f2c62b4d9446da32220625242012\"")
        }
    }
}

dependencies {

    api(libs.retrofit)
    api(libs.kotlinx.serialization.json)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.retrofit.serialization.ktx)
    implementation(libs.logging.interceptor)
    implementation(libs.compose.runtime)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}