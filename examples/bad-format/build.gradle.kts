plugins {
    id("org.dafny.dafny")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dafny {
    dafnyVersion.set("4.9.1")
}

 // Activate optional format *linter*
tasks.checkFormatDafny {
    enabled = true
}
