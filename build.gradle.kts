plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.10.1"
}

val pluginsVersion = "0.2.4"
val sshPlugin = "sshPlugin"
val dockerPlugin = "dockerPlugin"
description = "Easy deploy by SSH with docker"
version = pluginsVersion
group = "online.colaba"

repositories { mavenLocal(); mavenCentral(); jcenter() }

gradlePlugin {
    plugins {
        create(dockerPlugin) {
            id = "$group.docker"; implementationClass = "$group.DockerPlugin"
            description = "Docker needed tasks: all you need for easy deployment \uD83D\uDEE1️ "
        }
        create(sshPlugin) {
            id = "$group.ssh"; implementationClass = "$group.SshPlugin"
            description = "Ssh needed tasks for FTP deploy: all you need for easy deployment \uD83D\uDEE1️ "
        }
    }
}

pluginBundle {
    website = "https://github.com/steklopod/gradle-deploy-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-deploy-plugin.git"

    (plugins) {
        dockerPlugin {
            displayName = "Docker needed tasks"
            tags = listOf("docker", "kotlin", "deploy", "build.gradle.kts", "docker-compose")
            version = pluginsVersion
        }
        sshPlugin {
            displayName = "SSH task for easy deploy"
            tags = listOf("ssh", "kotlin", "deploy", "sftp", "ftp", "docker", "docker-compose")
            version = pluginsVersion
        }

    }
}

dependencies { implementation("org.hidetake:groovy-ssh:2.10.1") }

kotlinDslPluginOptions { experimentalWarning.set(false) }

tasks {
    wrapper { gradleVersion = "6.0" }
    val java = "11"
    compileKotlin { kotlinOptions { jvmTarget = java }; sourceCompatibility = java; targetCompatibility = java }
}

defaultTasks("tasks", "publishPlugins")
