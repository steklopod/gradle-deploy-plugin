import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.10.1"
}

val pluginsVersion = "0.1.9"
description = "EASY-DEPLOY gradle needed tasks"
version = pluginsVersion
group = "online.colaba"

repositories { mavenLocal(); mavenCentral() }

val sshPlugin = "sshPlugin"
val dockerPlugin = "dockerPlugin"
val dockerMainPlugin = "dockerMainPlugin"

gradlePlugin {
    plugins {
        create(dockerPlugin) {
            id = "$group.docker"; implementationClass = "$group.DockerPlugin"
            description = "Docker needed tasks"
        }
        create(dockerMainPlugin) {
            id = "$group.dockerMain"; implementationClass = "$group.DockerMainPlugin"
            description = "Docker needed tasks for root multi-project"
        }
        create(sshPlugin) {
            id = "$group.ssh"; implementationClass = "$group.SshPlugin"
            description = "Ssh needed tasks for FTP deploy"
        }
    }

}

pluginBundle {
    website = "https://github.com/steklopod/gradle-deploy-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-deploy-plugin.git"

    (plugins) {
        dockerPlugin {
            displayName = "\uD83D\uDEE1️ Docker needed tasks"
            tags = listOf("docker", "kotlin", "deploy", "build.gradle.kts", "docker-compose", "\uD83E\uDD1F\uD83C\uDFFB")
            version = pluginsVersion
        }
        dockerMainPlugin {
            displayName = "\uD83D\uDEE1️ Gradle dockerMain plugin for root multi-project"
            tags = listOf("docker", "kotlin", "deploy", "build.gradle.kts", "docker-compose", "\uD83E\uDD1F\uD83C\uDFFB")
            version = pluginsVersion
        }
        sshPlugin {
            displayName = "\uD83D\uDEE1️ SSH task for easy deploy"
            tags = listOf("ssh", "kotlin", "deploy", "sftp", "ftp", "\uD83E\uDD1F\uD83C\uDFFB")
            version = pluginsVersion
        }

    }
}

dependencies {
    implementation("org.hidetake:groovy-ssh:2.10.1")
}

configure<JavaPluginConvention> { sourceCompatibility = VERSION_11; targetCompatibility = VERSION_11 }

kotlinDslPluginOptions { experimentalWarning.set(false) }

tasks {
    withType<Wrapper> { gradleVersion = "6.0" }
    withType<KotlinCompile> { kotlinOptions { jvmTarget = "11" } }
}

defaultTasks("tasks", "publishPlugins")

