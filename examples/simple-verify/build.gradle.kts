plugins {
    id("org.dafny-lang.dafny")
    `java-library`
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks {
    dafnyTranslate {
        sourceDir.set(file("src"))
    }
}