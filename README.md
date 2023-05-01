# Dafny Gradle Plugin

This plugin offers tight integration of the 
[Dafny verification-aware programming language](httsp://dafny.org) with Java,
and a robust approach to distributing and managing Dafny dependencies
through Gradle-supported repositories such as Maven Central.

## Installation

The Dafny Gradle plugin is applied using the `org.dafny.dafny` plugin ID.
For example:

```kotlin
plugins {
    id("org.dafny.dafny").version("0.1.0")
}

dafny {
    dafnyVersion.set("4.1.0")
}

```

## Documentation

The plugin provides two tasks:

* `verifyDafny`, which verifes all Dafny code under the
  `src/main/dafny` directory.
* `translateDafnyToJava`, which translates Dafny code
  verified by `verifyDafny`
  to Java code, and ensures the result is also compiled
  together with the `main` Java source set.

Both tasks are executed automatically as dependencies of tasks
from the Java plugin, but can be executed manually as well.

The plugin also embeds the built Dafny code in the resulting Jar file,
and automatically discovers any Dafny code embedded in this way
in the project's dependencies, making them available to the Dafny tool as libraries.
This allows Dafny projects to define Dafny dependencies and distribute
themselves as libraries through Gradle-supported repositories such as Maven central.
For details see [this example](examples/multi-project/).

Projects can also configure command-line options to pass to the Dafny CLI
in a custom extension:

```kotlin
dafny {
    dafnyVersion.set("4.1.0")
  
    optionsMap.put("unicode-char", false)
    optionsMap.put("isolate-assertions", true)
}
```

## Known Limitations

* The collection of Dafny files to verify or translate is not yet configurable.
* The plugin only supports a `main` source set, and only integrates with Java code in the Java plugin's `main` source set.
* The Dafny CLI is only resolved through the current PATH, and there is no support for bootstrapping it.

## License

This library is licensed under the Apache 2.0 License. 
