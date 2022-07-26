import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

val localProperties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.raudonikis.network"
    defaultConfig {
        val fixerApiKey = "FIXER_API_KEY"
        buildConfigField("String", fixerApiKey, localProperties.getProperty(fixerApiKey))
    }
}

dependencies {
    implementation(project(Modules.Libraries.common))
    // Networking
    api(Dependencies.retrofit)
    implementation(Dependencies.moshi)
    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.moshiAdapters)
    implementation(Dependencies.okHttpInterceptor)
    // DI
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerCompiler)
}
