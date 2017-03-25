package com.github.admarple.gradle.resources

import org.gradle.api.DefaultTask
import org.gradle.api.file.EmptyFileVisitor
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction


/**
 * Build a manifest of all resources.  This will be used by the plugins at runtime to identify the resources available
 * in the jar.
 */
class WriteResourceManifest extends DefaultTask {
    private String manifestName = project.name + '_resources_manifest.txt'
    private sourceSet = project.sourceSets.main;

    @OutputFile
    def File getOutputFile() {
        return new File(sourceSet.output.resourcesDir, "quidsi_resources_manifest.txt")
    }

    /**
     * Write a file containing the relative paths to all resources included in this project, one per line.
     * @return
     */
    @TaskAction
    def writeResourceManifest() {
        def resources = new LinkedList<String>()
        sourceSet.resources.srcDirs.each {
            def resourceTree = project.files(it).getAsFileTree()

            def resourceTreeRoot = it.toURI()
            resourceTree.visit( new EmptyFileVisitor() {
                @Override
                void visitFile(FileVisitDetails fileDetails) {
                    String relativeFileName = resourceTreeRoot.relativize(fileDetails.file.toURI()).toString();
                    if (isVendedResource(relativeFileName)) {
                        resources.addLast(relativeFileName)
                    }
                }
            })
        }
        getOutputFile().write(String.join('\n', resources))
    }

    /**
     * Exclude resources from the manifest if they aren't needed by consumers.
     * @param resourceName
     * @return
     */
    boolean isVendedResource(String resourceName) {
        return !resourceName.startsWith("META-INF")
    }

    public WriteResourceManifest manifestName(String name) {
        this.manifestName = name
        return this
    }
}