plugins {
    id("org.dafny.dafny")

    `maven-publish`
}

dependencies {
    implementation("org.dafny:DafnyRuntime:4.0.0")
}

dafny {
    optionsMap.put("unicode-char", false)
}