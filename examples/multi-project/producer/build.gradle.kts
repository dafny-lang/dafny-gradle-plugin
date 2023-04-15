plugins {
    id("org.dafny-lang.dafny")
}

dependencies {
    implementation("org.dafny:DafnyRuntime:4.0.0")
}

sourceSets {
    main {
        java {
            srcDir("src/main/dafny/timesTwo-java")
        }
    }
}

tasks.jar {
    from("src/main/dafny/timesTwo-java") {
        include("META-INF/Program.doo")
    }
}

tasks {
    dafnyTranslate {
        sourceDir.set(file("src"))
    }
}
