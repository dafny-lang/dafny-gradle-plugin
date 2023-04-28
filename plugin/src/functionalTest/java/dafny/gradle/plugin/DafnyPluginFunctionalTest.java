/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dafny.gradle.plugin;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.io.FileWriter;

import org.gradle.api.GradleException;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.UnexpectedBuildFailure;
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
        assertThrows(UnexpectedBuildFailure.class, runner::build);
        // TODO: assert verification output
    }

    @Test void canReferenceDependencies() throws IOException {
        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withArguments("build");
        runner.withProjectDir(new File("../examples/multi-project"));
        BuildResult result = runner.build();
    }
}
