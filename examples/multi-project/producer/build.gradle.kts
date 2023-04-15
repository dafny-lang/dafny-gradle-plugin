plugins {
    id("org.dafny-lang.dafny")
}

dependencies {
}

tasks {
    verify {
        sourceDir.set(file("src"))
    }
}
