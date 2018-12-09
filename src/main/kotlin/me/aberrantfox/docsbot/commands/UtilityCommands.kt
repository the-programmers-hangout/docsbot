package me.aberrantfox.docsbot.commands

import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands

@CommandSet("utility")
fun utility() = commands {
    command("ping") {
        description = "Display the bot ping"
        execute {
            it.respond("Pong! (${it.jda.ping}ms)")
        }
    }
}