package org.dafny.gradle.plugin;

import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

public abstract class DafnyExtension {

    abstract public Property<String> getDafnyVersion();

    abstract public MapProperty<String, Object> getOptionsMap();
}