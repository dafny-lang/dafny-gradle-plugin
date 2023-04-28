package dafny.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.jvm.tasks.Jar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

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
        args.addAll(getCommonArguments());
        args.add("-o");
        // TODO: Put this somewhere temporary and copy to actual Jar source
        args.add(getOutputPath().get().getAsFile().getPath());

        invokeDafnyCLI(args);

        // TODO: copy doo file into META-INF and add to Jar task somehow
//        getProject().getTasks().withType(Jar.class, task -> {
//            task.from("src/main/dafny/timesTwo-java").include("META-INF/Program.doo");
//        });
    }
}
