package dafny.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

public abstract class VerifyTask extends DefaultTask {

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
        System.out.println("classpath: " + getClasspath().getFiles());

        Runtime rt = Runtime.getRuntime();
        try {
            String args = getSourceDir()
                    .getAsFileTree()
                    .getFiles()
                    .stream()
                    .map(f -> f.getPath())
                    .collect(Collectors.joining(" "));

            Process pr = rt.exec("dafny verify " + args);

            int result = pr.waitFor();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int read;
            while (0 < (read = pr.getInputStream().read(buffer))) {
                baos.write(buffer, 0, read);
            }
            System.out.println(baos);

            // TODO: build to Java, attach <project>.doo file
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
