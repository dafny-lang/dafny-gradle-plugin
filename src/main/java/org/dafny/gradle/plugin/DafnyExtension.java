package org.dafny.gradle.plugin;

import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

public abstract class DafnyExtension {

  public abstract Property<String> getDafnyVersion();

  public abstract MapProperty<String, Object> getOptionsMap();
}
