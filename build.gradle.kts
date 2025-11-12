// Root build file - plugins will be applied in subprojects

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
