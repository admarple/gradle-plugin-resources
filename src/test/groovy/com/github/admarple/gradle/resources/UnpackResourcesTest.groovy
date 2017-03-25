package com.github.admarple.gradle.resources

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertTrue

// TODO Find a way to fix this unit test.  Alternatively, explore integration testing
@Ignore('''The test below builds a jar with a manifest written by the WriteResourceManifest task,
           adds that jar as a dependency to a project, and then attempts to unpack the resources
           from the jar.  However, UnpackResources uses the current ClassLoader to access resources
           from the jar.  AFAICT, adding dependencies to the ProjectBuilder-built Project does not
           actually modify the classpath, so the ClassLoader is unable to unpack resources.
        ''')
class UnpackResourcesTest extends BaseTest {
    private UnpackResources unpackResources
    private String testManifestName = 'manifest.txt'

    @Override
    @Before
    public void setup() {
        super.setup()

        // build the jar (including resources and the manifest)
        project.tasks.create('writeResourceManifest', WriteResourceManifest.class).manifestName(testManifestName).execute()
        project.tasks.getByName('processResources').execute()
        project.tasks.getByName('jar').execute()

        // create a new project with a dependency on the jar
        project = ProjectBuilder.builder().withProjectDir(testDir).build()
        project.pluginManager.apply('java')
        project.dependencies {
            compile project.fileTree(dir: "build/libs", include: "*.jar")
        }

        // use the same manifest name as was used during manifest creation
        unpackResources = project.tasks.create('unpackResources', UnpackResources.class)
        unpackResources.manifestName(testManifestName)
    }

    @Test
    public void testExecute() {
        unpackResources.execute()
        String[] buildFiles = project.getBuildDir().list()
        println buildFiles
        assertTrue(buildFiles.contains('vended.properties'))
    }
}
