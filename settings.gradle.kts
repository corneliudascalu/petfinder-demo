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
    }
    versionCatalogs {
        create("libs") {
            library("truth", "com.google.truth:truth:1.3.0")
            version("retrofit", "2.9.0")
            library("retrofit-main", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library(
                "retrofit-moshi",
                "com.squareup.retrofit2",
                "converter-moshi"
            ).versionRef("retrofit")
            library("androidx-core", "androidx.core:core-ktx:1.12.0")
            library("androidx-appcompat", "androidx.appcompat:appcompat:1.6.1")
            library("google-material", "com.google.android.material:material:1.11.0")

            library("junit", "junit:junit:4.13.2")
            library("mockk", "io.mockk:mockk:1.13.9")
            library("androidx-junit", "androidx.test.ext:junit:1.1.5")
            library("androidx-espresso", "androidx.test.espresso:espresso-core:3.5.1")
        }
    }
}

rootProject.name = "Petfinder Demo"
include(":main")
include(":common")
include(":auth")
