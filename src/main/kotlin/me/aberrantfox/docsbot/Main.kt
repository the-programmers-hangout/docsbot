package me.aberrantfox.docsbot

import me.aberrantfox.docsbot.configuration.BotConfiguration
import me.aberrantfox.docsbot.configuration.loadConfig
import me.aberrantfox.docsbot.services.DocGrabber
import me.aberrantfox.docsbot.services.DocReader
import me.aberrantfox.docsbot.services.FormatRepository
import me.aberrantfox.docsbot.services.LanguageFormatter
import me.aberrantfox.kjdautils.api.dsl.PrefixDeleteMode
import me.aberrantfox.kjdautils.api.startBot
import org.reflections.Reflections
import javax.swing.text.html.HTML
import kotlin.reflect.full.declaredMemberProperties

private const val path = "me.aberrantfox.docsbot."

fun main(args: Array<String>) {
    val token = args.first()
    val config = loadConfig()

    if(config == null) {
        println("Configuration was generated, please fill it out.")
        return
    }

    start(token, config)
}

fun start(token: String, config: BotConfiguration) = startBot(token) {
    val docGrabber = DocGrabber(config).apply { pullAllDocs() }
    val reader = DocReader(config).apply { collectAllDocs() }
    val formatterRepository = FormatRepository.create()

    registerInjectionObject(config, docGrabber, reader, formatterRepository)

    configure {
        prefix = config.prefix
        commandPath = "${path}commands"
        deleteMode = PrefixDeleteMode.None
    }
}