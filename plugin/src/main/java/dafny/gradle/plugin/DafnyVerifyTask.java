package dafny.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.FilePropertyContainer;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.enterprise.test.FileProperty;

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
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public abstract class DafnyVerifyTask extends DafnyBaseTask {

    @InputFiles
    public abstract ConfigurableFileCollection getSourceFiles();

    @OutputFile
    public abstract RegularFileProperty getOutputPath();

    @TaskAction
    public void verify() throws IOException, InterruptedException {
        List<String> args = new ArrayList<>();
        args.add("build");
        args.add("-t:lib");
        for (var file : getSourceFiles().getFiles()) {
            args.add(file.getPath());
        }
        args.addAll(Utils.getCommonArguments(getClasspath().get(), getOptions().get()));
        args.add("-o");
        args.add(getOutputPath().get().getAsFile().getPath());

        invokeDafnyCLI(args);
    }
}
