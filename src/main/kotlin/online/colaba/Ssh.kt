package online.colaba

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.delegateClosureOf
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.hidetake.groovy.ssh.Ssh
import org.hidetake.groovy.ssh.connection.AllowAnyHosts
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.core.RunHandler
import org.hidetake.groovy.ssh.core.Service
import org.hidetake.groovy.ssh.session.SessionHandler
import java.io.File

const val sshGroup = "ssh"

open class Ssh : DefaultTask() {
    init {
        group = sshGroup
        description = "Publish by FTP your distribution with SSH commands"
    }

    companion object {
        private val ssh = Ssh.newService()
        private const val defaultUser = "root"
        private const val defaultHost = "colaba.online"
        private const val rsaKeyName = "id_rsa"
        private const val childBackendBuildFolder = "$backendService/$buildGroup"

        private val defaultRsaPath = "$userHomePath/.ssh/$rsaKeyName".normalizeForWindows()

        const val backendDistFolder = "$childBackendBuildFolder/libs"
        const val frontendBuildFolder = "$frontendService/dist"

        private val defaultServer = SshServer(mutableMapOf(), defaultHost, defaultUser, defaultRsaPath)

        private fun remote(
                targetHost: String = defaultHost,
                sshUser: String = defaultUser,
                idRsaPath: String = defaultRsaPath,
                settings: Map<String, Any>
        ): Remote {
            val remote = if (settings.isEmpty()) Remote(sshUser) else Remote(settings)
            return remote.apply { host = targetHost; user = sshUser; identity = File(idRsaPath) }
        }

        private fun remote(server: SshServer = defaultServer): Remote = remote(server.host, server.user, server.idRsaPath, server.config)

    }

    @get:Input
    var host = defaultHost
    @get:Input
    var user: String = defaultUser
    @get:Input
    var idRsaPath: String = defaultRsaPath
    @get:Input
    var server: SshServer = defaultServer
    @get:Input
    var allowAnyHosts: Boolean = true
    @get:Input
    @Optional
//    @InputDirectory
    var directory: String? = null
    @get:Input
    @Optional
    var toFolder: String? = null
    @get:Input
    @Optional
    var command: String? = null

    @TaskAction
    fun run() {
        val rootDir = project.rootDir

        val config = defaultServer.config

        if (File(rsaKeyName).exists()) {
            println("[$rsaKeyName] found in root of project")
            idRsaPath = rsaKeyName
            allowAnyHosts = true
        } else if (!File(defaultRsaPath).exists() /* TODO && idRsaPath == defaultRsaPath*/) {
            throw SshException("You don't have [$defaultRsaPath] file. Or you can put [$rsaKeyName] file in {$rootDir} directory.")
        }

        if (allowAnyHosts) {
            println("* If you want to scan [known_hosts] local file - set `allowAnyHosts = false` in gradle's ssh task.")
            config["knownHosts"] = AllowAnyHosts.instance
        }

        val server = remote(settings = config, targetHost = host, sshUser = user, idRsaPath = idRsaPath)

        ssh.runSessions {
            session(server) {
                command?.let {
                    println("\uD83D\uDD11 Executing  commands on remote server ($host): [ $command ]")
                    execute(it)
                }
                directory?.let {
                    val fromLocalPath = "$rootDir/$directory".normalizeForWindows()
                    val toRemoteDefault = fromLocalPath.substringAfter(rootDir.name + "/").substringBeforeLast("/")
                    val toRemote = "${rootDir.name}/${toFolder ?: toRemoteDefault}"

                    println("\n \uD83D\uDCE6 Copying from local  [$fromLocalPath] \n\t\t\t  to remote [$toRemote]")
                    execute("rm -fr $toRemote")
                    execute("mkdir --parent $toRemote")
                    put(File(fromLocalPath), toRemote)
                    println("\nOk. You can go to remote folder from your terminal with command:")
                    println("\n\tssh -t $user@$host \"cd $toRemote && bash\"")
                }
            }
        }
    }


    private fun Service.runSessions(action: RunHandler.() -> Unit) = run(delegateClosureOf(action))
    private fun RunHandler.session(vararg remotes: Remote, action: SessionHandler.() -> Unit) = session(*remotes, delegateClosureOf(action))
    private fun SessionHandler.put(from: Any, into: String) = put(hashMapOf("from" to from, "into" to into))
}

class SshException(override val message: String) : GradleException()

data class SshServer(val config: MutableMap<String, Any>, val host: String, val user: String, val idRsaPath: String)

fun Project.registerSshTask() = tasks.register<online.colaba.Ssh>(sshGroup)

val Project.ssh: TaskProvider<online.colaba.Ssh>
    get() = tasks.named<online.colaba.Ssh>(sshGroup)
