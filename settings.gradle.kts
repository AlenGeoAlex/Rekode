rootProject.name = "Rekode"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}


include("src:base")
include("src:entities")
include("src:abstraction")
include("src:plugin")