/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.dafny.gradle.plugin;

import org.gradle.api.Project;
import org.gradle.api.Plugin;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.jvm.tasks.Jar;

import java.io.File;

public class DafnyPlugin implements Plugin<Project> {

    public static final String META_INF_DOO_FILE_NAME = "DafnyProgram.doo";

    public void apply(Project project) {
        // Ensure that the Java plugin is applied.
        // TODO: make this optional as suggested by documentation:
        // https://docs.gradle.org/current/userguide/implementing_gradle_plugins.html#reacting_to_plugins
        project.getPluginManager().apply(JavaPlugin.class);

        DafnyExtension extension =
                project.getExtensions().create("dafny", DafnyExtension.class);

        TaskProvider<DafnyVersionCheckTask> dafnyVersionCheckProvider = project.getTasks()
                .register("checkDafnyVersion", DafnyVersionCheckTask.class);
        TaskProvider<DafnyVerifyTask> dafnyVerifyProvider = project.getTasks()
                .register("verifyDafny", DafnyVerifyTask.class);
        TaskProvider<DafnyTranslateTask> dafnyTranslateProvider = project.getTasks()
                .register("translateDafnyToJava", DafnyTranslateTask.class);

        // TODO: Configurable file collection in extension for Dafny source files,
        // possibly sourceSet with doo file location as output.
        FileCollection dafnySourceFiles = project.fileTree("src/main/dafny", t ->
                t.include("**/*.dfy").include("**/*.doo"));
        // TODO: More specific location
        File dooFile = new File(project.getBuildDir(), META_INF_DOO_FILE_NAME);
        // This is not automatically picked up as a source directory for Java compilation,
        // so we have to add it below, but it still seems like a good, idiomatic location.
        File translatedJavaDir = project.getBuildDir().toPath()
                .resolve("generated")
                .resolve("sources")
                .resolve("fromDafny")
                .resolve("java")
                .resolve("main")
                .toFile();

        dafnyVersionCheckProvider.configure(dafnyVersionCheckTask -> {
            dafnyVersionCheckTask.getClasspath().set(project.getConfigurations().getByName("compileClasspath"));
            dafnyVersionCheckTask.getRequiredVersion().set(extension.getDafnyVersion());
        });
        dafnyVerifyProvider.configure(dafnyVerifyTask -> {
            dafnyVerifyTask.dependsOn(dafnyVersionCheckProvider);

            dafnyVerifyTask.getSourceFiles().from(dafnySourceFiles);
            dafnyVerifyTask.getClasspath().set(project.getConfigurations().getByName("compileClasspath"));
            dafnyVerifyTask.getOptions().set(extension.getOptionsMap());
            dafnyVerifyTask.getOutputPath().set(dooFile);
        });
        dafnyTranslateProvider.configure(dafnyTranslateTask -> {
            dafnyTranslateTask.dependsOn(dafnyVersionCheckProvider);
            dafnyTranslateTask.dependsOn(dafnyVerifyProvider);

            dafnyTranslateTask.getDooFile().set(dooFile);
            dafnyTranslateTask.getClasspath().set(project.getConfigurations().getByName("compileClasspath"));
            dafnyTranslateTask.getOptions().set(extension.getOptionsMap());
            dafnyTranslateTask.getOutputPath().set(translatedJavaDir);
        });

        // Register the Dafny-generated code to be compiled
        JavaPluginExtension javaExt = project.getExtensions().findByType(JavaPluginExtension.class);
        javaExt.getSourceSets().findByName("main").getJava().srcDir(translatedJavaDir);

        // Make sure the Dafny-generated code is generated before compiling
        project.getTasks().withType(JavaCompile.class, javaCompile ->
            javaCompile.dependsOn(dafnyTranslateProvider)
        );

        // Embed the .doo file in the .jar file's META-INF
        project.getTasks().withType(Jar.class, jarTask -> {
            jarTask.metaInf(metaInf -> metaInf.from(dooFile.getPath()));
        });
    }
}