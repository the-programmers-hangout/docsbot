package me.aberrantfox.docsbot.commands

import me.aberrantfox.docsbot.services.DocGrabber
import me.aberrantfox.docsbot.services.DocReader
import me.aberrantfox.docsbot.services.LanguageFormatter
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.internal.command.arguments.WordArg


@CommandSet("docs")
fun mdn(docGrabber: DocGrabber, docReader: DocReader, formatter: LanguageFormatter) = commands {
    command("updatedocs") {
        description = "Update all of the available documentation"
        execute {
            it.respond("Pulling all docs")
            docGrabber.pullAllDocs()
        }
    }

    command("js") {
        description = "Lookup a javascript term"
        expect(WordArg)
        execute {
            val term = it.args.component1() as String
            val result = docReader.lookup("javascript", term)

            if(result == null) {
                it.respond("unknown")
            } else {
                it.respond(formatter.format("javascript", result))
            }
        }
    }
}