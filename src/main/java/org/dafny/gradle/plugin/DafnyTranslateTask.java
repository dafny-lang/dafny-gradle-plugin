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
        args.addAll(Utils.getCommonArguments(getClasspath().get(), getOptionsForTranslate()));

        args.add("-o");
        args.add(outputDir.getPath());

        invokeDafnyCLI(args);

        // Annoyingly, Dafny adds "-java" to the directory, so rename it
        File outputDirWithJava = new File(outputDir.getParentFile(), outputDir.getName() + "-java");
        outputDirWithJava.renameTo(outputDir);
    }

    private Map<String, Object> getOptionsForTranslate() {
        // --standard-libraries needs special handling,
        // since we first build a .doo file and then translate that.
        // That means the standard library code is already in the source .doo file,
        // and if we pass --standard-libraries against we end up with duplicate definitions.
        //
        // Note that there will also be options that can only be passed to `translate` and not `verify`.
        // Rather than duplicate the metadata about which options apply to which command,
        // we should generate a project file and pass it to Dafny,
        // because then Dafny itself will select the applicable subset for us.
        var options = new HashMap<>(getOptions().get());
        options.remove("standard-libraries");
        return options;
    }
}
