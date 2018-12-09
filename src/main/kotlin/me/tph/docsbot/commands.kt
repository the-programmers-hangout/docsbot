package me.tph.docsbot

import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.command.arguments.SentenceArg
import me.tph.docsbot.languages.fetch
import me.tph.docsbot.languages.javascript


@CommandSet("Docs")
fun mdn() = commands {
    command("mdn") {
        expect(SentenceArg)
        execute {
            val out = javascript(it.args.component1() as String)
            it.respond(out)
        }
    }
    command("ping") {
        execute {
            it.respond("pong")
        }
    }
}