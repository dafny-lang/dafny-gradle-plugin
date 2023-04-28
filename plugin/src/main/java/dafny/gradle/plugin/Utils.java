package dafny.gradle.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

public class Utils {

    public static List<String> DooFilesForClasspath(Collection<File> classpath) throws IOException {
        List<String> dooFiles = new ArrayList<>();
        for (File classpathEntry : classpath) {
            if (classpathEntry.getName().endsWith(".jar")) {
                JarFile jarFile = new JarFile(classpathEntry);
                ZipEntry dooEntry = jarFile.getEntry("META-INF/Program.doo");
                if (dooEntry != null) {
                    InputStream dooStream = jarFile.getInputStream(dooEntry);
                    Path extractedDoo = Files.createTempFile(classpathEntry.getName(), ".dfy");
                    OutputStream extractedDooStream = new FileOutputStream(extractedDoo.toFile());
                    drain(dooStream, extractedDooStream);

                    dooFiles.add(extractedDoo.toString());
                }
            }
            // TODO: handle class directories too
        }

        return dooFiles;
    }

    public static void drain(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        int read;
        while (0 < (read = in.read(buffer))) {
            out.write(buffer, 0, read);
        }
    }
}
