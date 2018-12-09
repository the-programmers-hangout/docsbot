package me.aberrantfox.docsbot.commands

import me.aberrantfox.docsbot.services.DocGrabber
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands


@CommandSet("Docs")
fun mdn(docGrabber: DocGrabber) = commands {
    command("updatedocs") {
        description = "Update all of the available documentation"
        execute {
            it.respond("Pulling all docs")
            docGrabber.pullAllDocs()
        }
    }
}