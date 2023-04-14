plugins {
    id("org.dafny-lang.dafny")
    `java-library`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Just for e.g.
    implementation("software.amazon.smithy:smithy-model:1.28.0")
}

tasks {
    verify {
        sourceDir.set(file("src"))
    }
}