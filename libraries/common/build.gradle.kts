plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    buildFeatures.viewBinding = true
    namespace = "com.raudonikis.common"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin
    api(Dependencies.kotlinStdLib)
    api(Dependencies.ktxCore)
    api(Dependencies.kotlinCoroutines)
    // Support
    api(Dependencies.appCompat)
    api(Dependencies.constraintLayout)
    api(Dependencies.recyclerView)
    api(Dependencies.material)
    // Lifecycle
    api(Dependencies.lifecycle)
    // Logging
    api(Dependencies.timber)
    // Hilt
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerCompiler)
}
