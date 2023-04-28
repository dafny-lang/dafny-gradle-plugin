package dafny.gradle.plugin;

import org.gradle.api.provider.MapProperty;

public abstract class DafnyExtension {

    abstract public MapProperty<String, Object> getOptionsMap();
}