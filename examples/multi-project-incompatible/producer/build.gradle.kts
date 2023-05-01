plugins {
    id("org.dafny.dafny")

    `maven-publish`
}

dependencies {
    // TODO: Replace with 4.1.0 once released
    implementation("org.dafny:DafnyRuntime:4.0.0")
}

dafny {
    dafnyVersion.set("4.1.0")

    optionsMap.put("unicode-char", false)
}