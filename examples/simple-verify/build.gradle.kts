plugins {
    id("dafny.gradle.plugin.greeting")
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks {
    verify {
        sourceDir.set(file("src"))
    }
}