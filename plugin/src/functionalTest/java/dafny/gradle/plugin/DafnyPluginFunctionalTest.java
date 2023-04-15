/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dafny.gradle.plugin;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.io.FileWriter;

import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A simple functional test for the 'dafny.gradle.plugin.greeting' plugin.
 */
class DafnyPluginFunctionalTest {
    @TempDir
    File projectDir;

    @Test void canVerify() throws IOException {
        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withArguments("build");
        runner.withProjectDir(new File("../examples/simple-verify"));
        BuildResult result = runner.build();

        // Verify the result
        assertTrue(result.getOutput().contains("Dafny version:"));
    }

    @Test void canReferenceDependencies() throws IOException {
        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withArguments("build");
        runner.withProjectDir(new File("../examples/multi-project"));
        BuildResult result = runner.build();

        // Verify the result
        assertTrue(result.getOutput().contains("Dafny version:"));
    }
}
