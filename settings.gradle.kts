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
            library("retrofit-moshi", "com.squareup.retrofit2", "converter-moshi").versionRef("retrofit")
        }
    }
}

rootProject.name = "Petfinder Demo"
include(":app")
include(":common")
include(":auth")
