package me.aberrantfox.docsbot.services

import me.aberrantfox.docsbot.configuration.BotConfiguration
import org.jsoup.Jsoup
import java.io.File

private const val Root = "https://docs.devdocs.io/"
private const val RootDir = "docs"

private val rootDir = File(RootDir)

class DocGrabber(val config: BotConfiguration) {
    init { setup() }

    fun pullAllDocs() = config.languages.forEach { pullDocs(it) }

    fun pullDocs(language: String): Boolean {
        if( !(config.languages.contains(language)) ) return false

        if( !(rootDir.exists()) ) rootDir.mkdir()

        val content = Jsoup
            .connect(languageURL(language))
            .maxBodySize(0)
            .timeout(0)
            .ignoreContentType(true)
            .execute()
            .body()

        File("$RootDir${File.separator}$language.json").writeText(content)

        return true
    }

    private fun languageURL(language: String) = "$Root$language/db.json"

    private fun setup() = if(rootDir.exists()) Unit else pullAllDocs()

}


