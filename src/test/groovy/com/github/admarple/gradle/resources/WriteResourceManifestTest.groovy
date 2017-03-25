package com.github.admarple.gradle.resources

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test


class WriteResourceManifestTest {
    private final File testDir = new File("build/tmp/test")
    private Project project
    private WriteResourceManifest writeResourceManifest

    @Before
    public void setup() {
        project = ProjectBuilder.builder().withProjectDir(testDir).build()
        writeResourceManifest = project.tasks.create('writeResourcesManifest', WriteResourceManifest.class)
        writeResourceManifest.manifestName('resource_manifest.txt')
    }

    @After
    void tearDown() {
        if (testDir.exists()) {
            testDir.deleteDir()
        }
    }

    @Test
    public void testWriteManifest() {
        // TODO
    }
}
