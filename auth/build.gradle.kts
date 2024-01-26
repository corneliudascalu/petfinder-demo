plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.klaxit.hiddensecrets").version("0.2.1")
}

android {
    namespace = "com.riverpath.petfinderdemo.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    // Enable NDK build
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":features"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.retrofit.main)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)
    implementation(libs.okhttp.logging)
    implementation(libs.timber)
    testImplementation(libs.truth)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    // Needed for DateTime
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}