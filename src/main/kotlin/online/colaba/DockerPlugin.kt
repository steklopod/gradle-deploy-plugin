package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*


class DockerPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        description = "Docker needed tasks"

        val name = project.name

        registerNeededTasks()

        tasks {
            docker {}
            compose {}
            val composeDev by registering(DockerCompose::class) { isDev = true }

            val remove by registering(Docker::class) { exec = "rm -f ${project.name}" }
            register("stop", Docker::class) { exec = "stop $name" }

            register("recompose", DockerCompose::class) { dependsOn(remove); finalizedBy(compose) }
            register("recomposeDev", DockerCompose::class) { dependsOn(remove); finalizedBy(composeDev) }

            register("npm-install", DockerCompose::class) { npm("install") }
            register("npm-build", DockerCompose::class) { npmRun("build") }
            register("npm-generate", DockerCompose::class) { npmRun("generate") }
            register("npm-start", DockerCompose::class) { npmRun("start") }
        }
    }
}
