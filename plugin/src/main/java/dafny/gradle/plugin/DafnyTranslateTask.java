package dafny.gradle.plugin;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DafnyTranslateTask extends DafnyBaseTask {

    @OutputFile
    public abstract RegularFileProperty getDooFile();

    @OutputFile
    public abstract DirectoryProperty getOutputPath();

    @TaskAction
    public void translateToJava() throws IOException, InterruptedException {
        List<String> args = new ArrayList<>();
        args.add("translate");
        args.add("java");
        args.add(getDooFile().get().getAsFile().getPath());
        args.addAll(Utils.getCommonArguments(getClasspath().get(), getOptions().get()));
        args.add("-o");
        // TODO: Put this somewhere temporary and copy to actual Jar source
        args.add(getOutputPath().get().getAsFile().getPath());

        invokeDafnyCLI(args);

        // TODO: copy doo file into META-INF and add to Jar task somehow
//        getProject().getTasks().withType(Jar.class, task -> {
//            task.from("src/main/dafny/timesTwo-java").include("META-INF/Program.doo");
//        });
    }
}
