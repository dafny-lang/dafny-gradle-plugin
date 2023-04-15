package dafny.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.com.google.common.collect.Streams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public abstract class DafnyTranslateTask extends DefaultTask {

    @InputDirectory
    public abstract DirectoryProperty getSourceDir();

    private FileCollection classpath;

    @Classpath
    public FileCollection getClasspath() {
        return classpath;
    }

    public void setClasspath(FileCollection configuration) {
        this.classpath = configuration;
    }

    @TaskAction
    public void verify() {
        Runtime rt = Runtime.getRuntime();
        try {
            String args = getSourceDir()
                    .getAsFileTree()
                    .getFiles()
                    .stream()
                    .filter(f -> f.getName().endsWith(".dfy"))
                    .map(f -> f.getPath())
                    .collect(Collectors.joining(" "));

            // collect *.doo files from the classpath,
            // unzip and add --library arguments
            for (File classpathEntry : getClasspath().getFiles()) {
                if (classpathEntry.getName().endsWith(".jar")) {
                    JarFile jarFile = new JarFile(classpathEntry);
                    ZipEntry dooEntry = jarFile.getEntry("META-INF/Program.doo");
                    if (dooEntry != null) {
                        InputStream dooStream = jarFile.getInputStream(dooEntry);
                        Path extractedDoo = Files.createTempFile(classpathEntry.getName(), ".dfy");
                        OutputStream extractedDooStream = new FileOutputStream(extractedDoo.toFile());
                        drain(dooStream, extractedDooStream);

                        args += " --library " + extractedDoo.toString();
                    }
                }
                // TODO: handle class directories too
            }

            Process pr = rt.exec("dafny translate java " + args);

            int result = pr.waitFor();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            drain(pr.getInputStream(), baos);
            System.out.println(baos);

            // TODO: build to Java, attach <project>.doo file
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void drain(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        int read;
        while (0 < (read = in.read(buffer))) {
            out.write(buffer, 0, read);
        }
    }
}
