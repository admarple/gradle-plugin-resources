package com.github.admarple.gradle.resources

import org.gradle.api.Project
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertTrue
import static org.junit.Assert.fail


class WriteResourceManifestTest extends BaseTest {
    private final File testDir = new File("build/tmp/test")
    private Project project
    private WriteResourceManifest writeResourceManifest

    @Override
    @Before
    public void setup() {
        super.setup()
        writeResourceManifest = project.tasks.create('writeResourcesManifest', WriteResourceManifest.class)
        writeResourceManifest.manifestName('resource_manifest.txt')
    }

    @Test
    public void testExecute() {
        writeResourceManifest.execute()

        List<String> manifestLines = writeResourceManifest.getOutputFile().readLines()
        assertTrue(manifestLines.contains('vended.properties'))
        manifestLines.each {
            if (it.contains('unvended.properties')) {
                fail('unvended.properties should be excluded')
            }
        }
    }
}
