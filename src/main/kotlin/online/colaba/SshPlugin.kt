package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register


class SshPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        description = "FTP deploy needed ssh-tasks"

        registerSshTask()

        ssh {
            frontend = false
            backend = false
            gradle = false
            static = false
            docker = false
            nginx = false
            run = "cd ${project.name} && echo \$PWD"
        }

        tasks {
            register(publishFront, Ssh::class) { frontend = true }

            register("publish", Ssh::class) {
                gradle = true
                frontend = true
                backend = true
                static = true
                docker = true
                nginx = true

                run = "echo \$PWD"
            }

            register("publishGradle", Ssh::class) { gradle = true }
            register("publishDocker", Ssh::class) { docker = true }
            register("publishStatic", Ssh::class) { static = true }
            register("publishBack", Ssh::class) { backend = true }
        }
    }
}
