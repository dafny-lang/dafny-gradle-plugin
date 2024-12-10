plugins {
    id("org.dafny.dafny")
}

dependencies {
    implementation(project(":producer"))

    implementation("org.dafny:DafnyRuntime:4.9.1")
}

dafny {
    dafnyVersion.set("4.9.1")

    optionsMap.put("unicode-char", true)
}