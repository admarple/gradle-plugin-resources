package com.github.admarple.gradle.resources

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test


class UnpackResourcesTest {
    private final File testDir = new File("build/tmp/test")
    private Project project
    private UnpackResources unpackResources

    @Before
    public void setup() {
        project = ProjectBuilder.builder().withProjectDir(testDir).build()
        unpackResources = project.tasks.create('unpackResources', UnpackResources.class)
    }

    @After
    void tearDown() {
        if (testDir.exists()) {
            testDir.deleteDir()
        }
    }

    @Test
    public void testResourcesUnpacked() {
        // TODO
    }
}
