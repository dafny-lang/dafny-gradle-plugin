plugins {
    id("org.dafny-lang.dafny")
}

dependencies {
    implementation(project(":producer"))
}

tasks {
    dafnyTranslate {
        sourceDir.set(file("src"))
    }
}
