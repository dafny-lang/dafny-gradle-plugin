plugins {
    id("org.dafny.dafny")

    `maven-publish`
}

dependencies {
    implementation("org.dafny:DafnyRuntime:4.9.1")
}

dafny {
    dafnyVersion.set("4.9.1")

    optionsMap.put("unicode-char", false)
}