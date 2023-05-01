plugins {
    id("org.dafny.dafny")
}

dafny {
    dafnyVersion.set("4.1.0")
}

dependencies {
    implementation(project(":producer"))

    // TODO: Replace with 4.1.0 once released
    implementation("org.dafny:DafnyRuntime:4.0.0")
}
