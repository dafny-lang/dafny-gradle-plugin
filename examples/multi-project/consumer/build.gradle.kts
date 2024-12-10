plugins {
    id("org.dafny.dafny")
}

dafny {
    dafnyVersion.set("4.9.1")
}

dependencies {
    implementation(project(":producer"))

    implementation("org.dafny:DafnyRuntime:4.9.0")
}
