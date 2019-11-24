package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*


class DockerPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        description = "Docker needed tasks"

        val name = project.name

        registerExecutorTask()
        registerDockerTask()

        tasks {
            docker {

            }
            compose {

            }
            register("stop", Docker::class) { command = "$dockerPrefix stop $name" }

            val remove by registering(Docker::class) { exec = "rm -f ${project.name}" }

            val deployDev by registering(DockerCompose::class) { isDev = true }

            register("redeploy", DockerCompose::class) { dependsOn(remove); finalizedBy(compose) }
            register("redeployDev", DockerCompose::class) { dependsOn(remove); finalizedBy(deployDev) }

            register("npm-install", DockerCompose::class) { npm("install") }
            register("npm-build", DockerCompose::class) { npmRun("build") }
            register("npm-generate", DockerCompose::class) { npmRun("generate") }
            register("npm-start", DockerCompose::class) { npmRun("start") }
        }
    }
}
