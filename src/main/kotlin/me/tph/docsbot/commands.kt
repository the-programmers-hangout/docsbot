package me.tph.docsbot

import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.command.arguments.SentenceArg
import me.tph.docsbot.docs.fetch



@CommandSet("Docs")
fun mdn() = commands {
    command("mdn") {
        expect(SentenceArg)
        execute {
            fetch(it.args.component1() as String)?.let { item ->
                val out = "```js\n$item\n```"
                it.respond(out)
            }
        }
    }
    command("ping") {
        execute {
            it.respond("pong")
        }
    }
}