package org.dafny.gradle.plugin;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DafnyTranslateTask extends DafnyBaseTask {

    @OutputFile
    public abstract RegularFileProperty getDooFile();

    @OutputDirectory
    public abstract DirectoryProperty getOutputPath();

    @TaskAction
    public void translateToJava() throws IOException, InterruptedException {
        var dooFile = getDooFile().get().getAsFile();
        var outputDir = getOutputPath().get().getAsFile();

        if (!dooFile.exists()) {
            return;
        }

        List<String> args = new ArrayList<>();
        args.add("translate");
        args.add("java");
        args.add(dooFile.getPath());
        args.addAll(Utils.getCommonArguments(getClasspath().get(), getOptions().get()));

        args.add("-o");
        args.add(outputDir.getPath());

        invokeDafnyCLI(args);
    }
}
