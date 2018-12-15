package me.aberrantfox.docsbot.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import me.aberrantfox.docsbot.configuration.BotConfiguration
import me.aberrantfox.docsbot.configuration.DirectoryConfiguration
import me.aberrantfox.docsbot.configuration.loadConfig
import me.aberrantfox.docsbot.services.DocGrabber
import me.aberrantfox.docsbot.services.DocReader
import me.aberrantfox.docsbot.services.FormatRepository
import me.aberrantfox.docsbot.utility.CLIConstants
import me.aberrantfox.docsbot.utility.FileConstants
import me.aberrantfox.kjdautils.api.dsl.PrefixDeleteMode
import me.aberrantfox.kjdautils.api.startBot

class CommandLineParser : CliktCommand(help = "You MUST provide a --token which is a valid Discord bot token!") {
    val token: String by option("-t", "--token", help = CLIConstants.TokenMessage).required()
    val rootDir: String by option("-r", "-d", "--rootdir", "--dir", help = CLIConstants.RootDirectoryMessage)
            .default(FileConstants.Project_Directory_Default)

    override fun run() {
        val config = loadConfig(rootDir)

        if(config == null) {
            echo("Configuration was generated, please fill it out.")
            return
        }

        start(token, config, rootDir)
    }
}

private const val path = "me.aberrantfox.docsbot."

fun start(token: String, config: BotConfiguration, rootDir: String) = startBot(token) {
    val dirConfiguration = DirectoryConfiguration(rootDir)
    val docGrabber = DocGrabber(config, dirConfiguration).apply { pullAllDocs() }
    val reader = DocReader(config, dirConfiguration).apply { collectAllDocs() }
    val formatterRepository = FormatRepository.create()

    registerInjectionObject(config, docGrabber, reader, formatterRepository, dirConfiguration)

    configure {
        prefix = config.prefix
        commandPath = "${path}commands"
        deleteMode = PrefixDeleteMode.None
    }
}