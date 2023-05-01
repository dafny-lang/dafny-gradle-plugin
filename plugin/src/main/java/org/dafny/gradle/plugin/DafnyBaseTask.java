package org.dafny.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public abstract class DafnyBaseTask extends DefaultTask {

    @Input
    public abstract MapProperty<String, Object> getOptions();

    @Classpath
    public abstract Property<FileCollection> getClasspath();

    public record DafnyCLIResult(int exitCode, String stdout, String stderr) {}

    protected DafnyCLIResult invokeDafnyCLI(List<String> args) throws InterruptedException, IOException {
        getProject().getLogger().info("Executing Dafny CLI: " + String.join(" ", args));

        // TODO: This is probably a nice utility for managing subprocesses buried somewhere in the Gradle framework
        // we could reuse, especially to avoid potential deadlock on failing to read the output streams.
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("dafny " + String.join(" ", args));
        int exitCode = pr.waitFor();

        ByteArrayOutputStream outBaos = new ByteArrayOutputStream();
        Utils.drain(pr.getInputStream(), outBaos);
        String output = outBaos.toString();

        ByteArrayOutputStream errBaos = new ByteArrayOutputStream();
        Utils.drain(pr.getErrorStream(), errBaos);
        String error = errBaos.toString();

        if (exitCode != 0) {
            throw new GradleException("Dafny CLI returned a non-zero error code: " + exitCode
                                        + "\n" + output + "\n" + error);
        }

        return new DafnyCLIResult(exitCode, output, error);
    }
}
