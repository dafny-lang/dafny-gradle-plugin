plugins {
    id("org.dafny.dafny")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dafny {
    dafnyVersion.set("4.9.1")

    optionsMap.put("standard-libraries", true)
}

dependencies {
    implementation("org.dafny:DafnyRuntime:4.9.0")
}
