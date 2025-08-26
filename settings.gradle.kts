pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("http://maven.google.com/")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "CoffeePoints"
include(":app")
include(":features:authorization")
include(":dependencies:di")
include(":dependencies:ui")
include(":data")
include(":dependencies:common")
include(":core")
include(":features:coffeePointsList")
include(":features:coffeePointsMap")
include(":features:coffeePointsMenu")
include(":features:confirmScreen")
