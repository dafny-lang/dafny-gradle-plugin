plugins {
    id("org.dafny-lang.dafny")
}

dependencies {
    implementation(project(":producer"))
}

tasks {
    verify {
        sourceDir.set(file("src"))
    }
}
