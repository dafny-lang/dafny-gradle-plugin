rootProject.name = "multi-project"

include(":producer")
include(":consumer")

pluginManagement {
    repositories {
        mavenLocal()
        // Uncomment these to use the published version of the plugin from your preferred source.
        // gradlePluginPortal()
         mavenCentral()
    }
}
