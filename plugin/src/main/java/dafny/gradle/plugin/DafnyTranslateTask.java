package dafny.gradle.plugin;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DafnyTranslateTask extends DafnyBaseTask {

    @OutputFile
    public abstract RegularFileProperty getDooFile();

    @OutputFile
    public abstract DirectoryProperty getOutputPath();

    @TaskAction
    public void translateToJava() throws IOException, InterruptedException {
        List<String> args = new ArrayList<>();
        args.add("translate");
        args.add("java");
        args.add(getDooFile().get().getAsFile().getPath());
        args.addAll(Utils.getCommonArguments(getClasspath().get(), getOptions().get()));

        var outputDir = getOutputPath().get().getAsFile();
        args.add("-o");
        args.add(outputDir.getPath());

        invokeDafnyCLI(args);

        // Annoyingly, Dafny adds "-java" to the directory, so rename it
        File outputDirWithJava = new File(outputDir.getParentFile(), outputDir.getName() + "-java");
        outputDirWithJava.renameTo(outputDir);
    }
}
