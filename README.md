# gradle-plugin-resources
Gradle tasks for including resources with a plugin

This plugin is useful to other Gradle plugins that want to use resource files in the
project to which they are being applied.

## Usage
For example, let's say we want to write a plugin, my-standard-checkstyle, that
applies a common set of checkstyle rules to any project that applies it.

When building the plugin, use the WriteResourceManifest task to include a manifest of
all resources that should be bundled with your plugin.

```groovy
// In my-standard-checkstyle/build.gradle
task resourceManifest(type: WriteResourceManifest)
resourceManifest.manifestName('my-standard-checkstyle-manifest.txt')
resourceManifest.dependsOn processResources
jar.dependsOn resourceManifest
```

When your plugin is applied, use the UnpackResources task to make resources available
in the project applying your plugin.

```groovy
// In MyStandardCheckstylePlugin.groovy
class MyStandardCheckstyle implements Plugin<Project> {
    void apply(Project project) {
        project.configure(project) {
            apply plugin: 'checkstyle'

            task('unpackStandardResources', type: UnpackStandardResources)
            project.tasks.classes.dependsOn unpackStandardResources

            checkstyle {
                configFile = new File(project.buildDir, 'path/to/my-standard-checkstyle.xml')
            }
        }
    }
}
```