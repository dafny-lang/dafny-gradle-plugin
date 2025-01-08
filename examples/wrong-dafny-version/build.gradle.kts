plugins {
    id("org.dafny.dafny")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dafny {
    dafnyVersion.set("2.3.0")
}
