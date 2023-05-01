plugins {
    id("org.dafny.dafny")

    `maven-publish`
}

dafny {
    dafnyVersion.set("4.1.0")
}

dependencies {
    implementation("org.dafny:DafnyRuntime:4.0.0")
}
