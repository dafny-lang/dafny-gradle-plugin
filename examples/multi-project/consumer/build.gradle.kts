plugins {
    id("org.dafny.dafny")
    application
}

dafny {
    dafnyVersion.set("4.1.0")
}

dependencies {
    implementation(project(":producer"))

    implementation("org.dafny:DafnyRuntime:4.0.0")
}
