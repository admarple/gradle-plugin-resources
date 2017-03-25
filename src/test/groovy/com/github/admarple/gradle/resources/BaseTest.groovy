package com.github.admarple.gradle.resources

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before


abstract class BaseTest {
    protected final File testDir = new File("build/tmp/test")
    protected Project project;

    @Before
    public void setup() {
        testDir.mkdirs()
        addResource()
        addUnvendedResource()
        project = ProjectBuilder.builder().withProjectDir(testDir).build()
        project.pluginManager.apply('java')
    }

    @After
    public void tearDown() {
        if (testDir.exists()) {
            testDir.deleteDir()
        }
    }

    private File testResourceDir() {
        def resourceDir = new File(testDir, 'src/main/resources')
        resourceDir.mkdirs()
        return resourceDir
    }

    private void addResource() {
        new File(testResourceDir(), 'vended.properties').write('foo=1')
    }

    private void addUnvendedResource() {
        def metaInfDir = new File(testResourceDir(), 'META-INF')
        metaInfDir.mkdirs()
        new File(metaInfDir, 'unvended.properties').write('bar=0')
    }
}
