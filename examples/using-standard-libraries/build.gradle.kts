plugins {
    id("org.dafny.dafny")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dafny {
    dafnyVersion.set("4.9.0")

    optionsMap.put("standard-libraries", true)
}