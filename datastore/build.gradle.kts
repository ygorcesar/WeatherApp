plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.kotlin.compose)
}

setupAndroidModule()

android {
    namespace = "com.challenge.datastore"
}

dependencies {
    api(libs.kotlinx.coroutines.android)
    api(libs.datastore.core)
    api(libs.datastore.preferences)
    implementation(libs.compose.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}