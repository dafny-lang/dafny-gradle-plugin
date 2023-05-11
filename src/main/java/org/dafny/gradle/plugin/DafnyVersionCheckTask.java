package org.dafny.gradle.plugin;

import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.util.List;

public abstract class DafnyVersionCheckTask extends DafnyBaseTask {

    @Input
    public abstract Property<String> getRequiredVersion();

    @TaskAction
    public void checkVersion() throws IOException, InterruptedException {
        List<String> args = List.of("--version");
        DafnyCLIResult result = invokeDafnyCLI(args);

        String expected = getRequiredVersion().get();
        String actual = result.stdout().trim();
        if (!expected.equals(actual)) {
            throw new GradleException("Incorrect Dafny version: expected " + expected + ", found " + actual);
        }
    }
}
