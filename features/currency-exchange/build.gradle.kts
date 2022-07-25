plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    kotlin("android")
    kotlin("kapt")
}

android {
    buildFeatures.viewBinding = true
    namespace = "com.raudonikis.currency_exchange"
}

dependencies {
    implementation(project(Modules.Libraries.common))
    implementation(project(Modules.Libraries.data))
    implementation(project(Modules.Libraries.network))
    // Hilt
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerCompiler)
    // Navigation
    implementation(Dependencies.navigationFragment)
}
