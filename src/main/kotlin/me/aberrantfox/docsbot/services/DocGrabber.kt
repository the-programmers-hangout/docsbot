package me.aberrantfox.docsbot.services

import me.aberrantfox.docsbot.configuration.BotConfiguration
import me.aberrantfox.docsbot.utility.FileConstants
import me.aberrantfox.docsbot.utility.URLConstants
import org.jsoup.Jsoup
import java.io.File


class DocGrabber(val config: BotConfiguration) {
    fun pullAllDocs() {
        FileConstants.Documentation_Directory_File.listFiles().forEach { it.delete() }
        config.languages.forEach { pullDocs(it) }
    }

    fun pullDocs(language: String): Boolean {
        if( !(config.languages.contains(language)) ) return false

        val content = Jsoup
            .connect(URLConstants.languageURL(language))
            .maxBodySize(0)
            .timeout(0)
            .ignoreContentType(true)
            .execute()
            .body()

        File(FileConstants.dbFilePath(language)).writeText(content)

        return true
    }
}


