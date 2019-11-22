package online.colaba

import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

open class DockerCompose : Executor() {
    init {
        group = dockerPrefix
        description = "Docker-compose task"
    }

    @get:Input
    var service = project.name

    @TaskAction
    override fun exec() {
        super.command = "$dockerPrefix-compose up $service"

        super.exec()
    }

/*
    fun containers() { command = "$dockerPrefix ps" }

    fun dockerCompose(dockerComposeCommand: String) { command = "$dockerPrefix-compose $dockerComposeCommand"; group = dockerPrefix }

    fun dockerComposeUp() { dockerCompose("up $detachFlag") }
    fun dockerComposeUpDev(fileName: String? = "$dockerPrefix-compose.dev.yml") { dockerCompose("-f $fileName up $detachFlag") }

    fun dockerComposeUpRebuild(command: String? = "") { dockerCompose("up $recreateFlags $command") }

    fun dockerComposeUpRebuildDev(fileName: String? = "$dockerPrefix-compose.dev.yml") { dockerCompose("-f $fileName up $recreateFlags") }
*/
}

fun Project.registerDockerComposeTask() = tasks.register<DockerCompose>("dockerCompose")

val Project.dockerCompose: TaskProvider<DockerCompose>
    get() = tasks.named<DockerCompose>("dockerCompose")
