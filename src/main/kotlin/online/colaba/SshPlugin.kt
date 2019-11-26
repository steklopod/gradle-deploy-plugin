package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*


class SshPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        description = "SSH needed deploy-tasks"

        registerSshTask()
        registerDockerComposeTask()

        ssh {   }

        compose{   }

        tasks {
            register(publishFront, Ssh::class) { frontend = true }

            register("publish", Ssh::class) {
                gradle = true
                frontend = true
                backend = true
                static = true
                docker = true
                nginx = true
                run = "cd ${project.name} && echo \$PWD"
            }

            register("publishGradle", Ssh::class) { gradle = true }
            register("publishDocker", Ssh::class) { docker = true }
            register("publishStatic", Ssh::class) { static = true }
            register("publishBack", Ssh::class) { backend = true }

            val composeDev by registering(DockerCompose::class) { dependsOn(":$backendService:assemble"); isDev = true }
            register("composeNginx", DockerCompose::class) { service = nginxService }
            register("composeBack", DockerCompose::class) { service = backendService }
            register("composeFront", DockerCompose::class) { service = frontendService }

            val removeBackAndFront by registering(Docker::class) { dependsOn(":$frontendService:$removeGroup"); finalizedBy(":$backendService:$removeGroup") }
            val removeAll by registering(Docker::class) { dependsOn(":$nginxService:$removeGroup"); finalizedBy(removeBackAndFront) }
            register("prune", Docker::class) { exec = "system prune -fa" }
            register("recomposeAll", DockerCompose::class) { dependsOn(removeAll); finalizedBy(compose) }
            register("recomposeAllDev", DockerCompose::class) { dependsOn(removeAll); finalizedBy(composeDev) }

        }
    }
}
