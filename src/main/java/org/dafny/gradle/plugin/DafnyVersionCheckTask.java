package org.dafny.gradle.plugin;

import java.io.IOException;
import java.util.List;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.semver4j.Semver;

public abstract class DafnyVersionCheckTask extends DafnyBaseTask {

  @Input
  public abstract Property<String> getRequiredVersion();

  @TaskAction
  public void checkVersion() throws IOException, InterruptedException {
    List<String> args = List.of("--version");
    DafnyCLIResult result = invokeDafnyCLI(args);

    Semver expected = Semver.parse(getRequiredVersion().get());
    Semver actual = Semver.parse(result.stdout().trim());
    if (!expected.isEquivalentTo(actual)) {
      throw new GradleException(
          "Incorrect Dafny version: expected " + expected + ", found " + actual);
    }
  }
}
