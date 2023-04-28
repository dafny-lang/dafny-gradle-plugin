package dafny.gradle.plugin;

import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.com.google.common.io.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class DafnyTranslateTask extends DafnyBaseTask {

    @OutputFile
    public abstract RegularFileProperty getDooFile();

    @OutputDirectory
    public abstract DirectoryProperty getOutputPath();

    @TaskAction
    public void translateToJava() throws IOException, InterruptedException {
        var dooFile = getDooFile().get().getAsFile();
        var outputDir = getOutputPath().get().getAsFile();

        List<String> args = new ArrayList<>();
        args.add("translate");
        args.add("java");
        args.add(dooFile.getPath());
        args.addAll(Utils.getCommonArguments(getClasspath().get(), getOptions().get()));

        args.add("-o");
        args.add(outputDir.getPath());

        invokeDafnyCLI(args);

        // Annoyingly, Dafny adds "-java" to the directory, so rename it
        File outputDirWithJava = new File(outputDir.getParentFile(), outputDir.getName() + "-java");
        outputDirWithJava.renameTo(outputDir);

        // Attach the doo file in META-INF
        File metaInfDir = new File(outputDir, "META-INF");
        metaInfDir.mkdirs();
        File dooFileInMetaInf = new File(metaInfDir, "Program.doo");
        try (InputStream dooIn = new FileInputStream(dooFile)) {
            try (OutputStream dooOut = new FileOutputStream(dooFileInMetaInf)) {
                Utils.drain(dooIn, dooOut);
            }
        }
    }
}
