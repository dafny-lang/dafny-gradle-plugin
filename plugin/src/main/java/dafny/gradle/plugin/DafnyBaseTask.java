package dafny.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DafnyBaseTask extends DefaultTask {

    @Input
    public abstract MapProperty<String, Object> getOptions();

    @Classpath
    public abstract Property<FileCollection> getClasspath();

    protected void invokeDafnyCLI(List<String> args) throws InterruptedException, IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("dafny " + String.join(" ", args));
        int result = pr.waitFor();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Utils.drain(pr.getInputStream(), baos);
        System.out.println(baos);

        if (result != 0) {
            throw new GradleException("Dafny CLI returned a non-zero error code: " + result);
        }
    }
}
