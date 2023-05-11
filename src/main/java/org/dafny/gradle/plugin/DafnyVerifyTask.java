package org.dafny.gradle.plugin;

import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DafnyVerifyTask extends DafnyBaseTask {

    @InputFiles
    public abstract ConfigurableFileCollection getSourceFiles();

    @OutputFile
    public abstract RegularFileProperty getOutputPath();

    @TaskAction
    public void verify() throws IOException, InterruptedException {
        if (getSourceFiles().isEmpty()) {
            // TODO: I'm not sure why Gradle doesn't mark this task with NO-SOURCE
            // and skip it automatically, given the @InputFiles annotation.
            return;
        }

        List<String> args = new ArrayList<>();
        args.add("build");
        args.add("-t:lib");
        args.addAll(Utils.getCommonArguments(getClasspath().get(), getOptions().get()));
        args.add("-o");
        args.add(getOutputPath().get().getAsFile().getPath());
        args.add("--");
        for (var file : getSourceFiles().getFiles()) {
            args.add(file.getPath());
        }

        invokeDafnyCLI(args);
    }
}
