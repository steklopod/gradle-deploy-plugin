package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering


class SshPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        description = "FTP deploy needed ssh-tasks"

        registerSshTask()

        ssh {
            command = "echo \$PWD"
        }

        tasks {
            val publishFront by registering(Ssh::class) {
                directory = Ssh.frontendBuildFolder; group = sshGroup
            }
            val publishBack by registering(Ssh::class) {
                directory = Ssh.backendDistFolder; group = sshGroup
            }
            val publish by registering {
                dependsOn(publishFront); finalizedBy(publishBack)
            }
        }
    }
}
