package org.dafny.gradle.plugin;

import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DafnyFormatTask extends DafnyBaseTask {

    @InputFiles
    public abstract ConfigurableFileCollection getSourceFiles();

    @Input
    public abstract Property<Boolean> getIsCheck();

    @TaskAction
    public void format() throws IOException, InterruptedException {

        if (getSourceFiles().isEmpty()) {
            return;
        }

        List<String> args = new ArrayList<>();
        args.add("format");
        if (getIsCheck().getOrElse(false)) {
            args.add("--check");
        }
        args.addAll(Utils.getCommonArguments(getClasspath().get(), getOptions().get()));
        args.add("--");
        for (var file : getSourceFiles().getFiles()) {
            args.add(file.getPath());
        }

        invokeDafnyCLI(args);
    }
}
