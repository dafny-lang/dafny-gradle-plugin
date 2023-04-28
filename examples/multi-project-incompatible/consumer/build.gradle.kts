plugins {
    id("org.dafny.dafny")
}

dependencies {
    implementation(project(":producer"))

    implementation("org.dafny:DafnyRuntime:4.0.0")
}

dafny {
    optionsMap.put("unicode-char", true)
}