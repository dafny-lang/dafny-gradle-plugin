plugins {
    id("org.dafny.dafny")

    `maven-publish`
}

dafny {
    dafnyVersion.set("4.1.0")

    optionsMap.put("function-syntax", 3)
}

dependencies {
    // TODO: Replace with 4.1.0 once released
    implementation("org.dafny:DafnyRuntime:4.0.0")
}
