package dafny.gradle.plugin;

import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.SourceSetContainer;

public abstract class DafnyExtension {

    abstract public MapProperty<String, Object> getOptions();

}
