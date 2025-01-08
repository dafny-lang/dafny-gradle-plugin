package org.dafny.gradle.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;

public class Utils {

  // TODO: Shape this as something Gradle can cache for us,
  // which will also ensure these are cleaned up as needed.
  public static List<String> dooFilesForClasspath(Collection<File> classpath) throws IOException {
    List<String> dooFiles = new ArrayList<>();
    for (File classpathEntry : classpath) {
      if (classpathEntry.getName().endsWith(".jar")) {
        JarFile jarFile = new JarFile(classpathEntry);
        // TODO: Look for any .doo file, or perhaps use an SPI?
        ZipEntry dooEntry = jarFile.getEntry("META-INF/" + DafnyPlugin.META_INF_DOO_FILE_NAME);
        if (dooEntry != null) {
          InputStream dooStream = jarFile.getInputStream(dooEntry);
          Path extractedDoo = Files.createTempFile(classpathEntry.getName(), ".doo");
          OutputStream extractedDooStream = new FileOutputStream(extractedDoo.toFile());
          dooStream.transferTo(extractedDooStream);

          dooFiles.add(extractedDoo.toString());
        }
      }
      // TODO: handle class directories too
    }

    return dooFiles;
  }

  public static List<String> getCommonArguments(
      FileCollection classpath, Map<String, Object> options) throws IOException {
    List<String> args = new ArrayList<>();

    for (var dooFile : Utils.dooFilesForClasspath(classpath.getFiles())) {
      args.add("--library");
      args.add(dooFile);
    }

    for (var entry : options.entrySet()) {
      args.add("--" + entry.getKey() + ":" + entry.getValue());
    }

    return args;
  }

  public static Configuration getCompileClasspath(Project project) {
    return project.getConfigurations().getByName("compileClasspath");
  }
}
