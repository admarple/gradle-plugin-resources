package com.github.admarple.gradle.resources

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


public class UnpackResources extends DefaultTask {
    private static final Charset CHARSET = StandardCharsets.UTF_8
    private String manifestName = project.name + '_resources_manifest.txt'

    /**
     * Unpack all of the resources listed in the manifest file.  They can then be used by the plugins that this plugin
     * will apply.
     *
     * The benefit of this is that this plugin can define gradle tasks, configuration, etc. that depends on external
     * resources.  For example, the checkstyle plugin supports a "config" extension for providing XML configuration.
     * If we want to be able to use a "common" XML file across multiple projects, that XML file needs to be available
     * to the application at build time.  This task unpacks all resources listed in the manifest so that they are
     * available in applications that apply this plugin.
     */
    @TaskAction
    public void unpackResources() {
        resources().each {
            println 'unpacking: ' + it
            def content = project.resources.text.fromString(resourceAsString(it))
            def contentFile = content.asFile(CHARSET.name())
            def destFile = new File(project.getBuildDir(), it)
            FileUtils.copyFile(contentFile, destFile)
        }
    }

    List<String> resources() {
        return resourceAsString(manifestName).readLines()
    }

    String resourceAsString(String resource) {
        def resourceStream = getClass().getClassLoader().getResourceAsStream(resource)
        if (resourceStream == null) {
            throw new RuntimeException('Unable to find resource: ' + resource)
        }
        return IOUtils.toString(resourceStream, CHARSET)
    }

    public UnpackResources manifestName(String name) {
        this.manifestName = name
        return this
    }
}
