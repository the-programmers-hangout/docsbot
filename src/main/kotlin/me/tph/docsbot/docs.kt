package me.tph.docsbot

import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.command.arguments.SentenceArg

data class DocsResponse(val body: String, val example: String?)

interface DocsHook {
    fun isValid(input: String): Boolean
    fun fetch(input: String): DocsResponse
}

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