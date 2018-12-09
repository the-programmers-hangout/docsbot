package me.aberrantfox.docsbot.services

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import me.aberrantfox.docsbot.configuration.BotConfiguration
import me.aberrantfox.docsbot.utility.FileConstants
import java.io.File
import java.util.concurrent.ConcurrentHashMap

private const val IndexKey = "index"

class DocReader(val config: BotConfiguration) {
    private val cache: ConcurrentHashMap<String, JsonObject> = ConcurrentHashMap()

    fun lookup(language: String, term: String): String? {
        val lowerLanguage = language.toLowerCase()

        if( !(cache.containsKey(lowerLanguage)) ) return null

        val keys = DocLookupHelper.getKeys(language, term) ?: return null
        val languageObj = cache[language]!!
        val key = keys.firstOrNull { languageObj.has(it) } ?: return null

        return languageObj[key].asString
    }

    fun collectAllDocs() = config.languages.forEach { collectDocs(it) }

    fun collectDocs(language: String) {
        val database = File(FileConstants.dbFilePath(language)).readText()
        val json = extractJsonObject(database)

        cache[language] = json
    }

    private fun extractJsonObject(database: String) =
            JsonParser().parse(database).asJsonObject.apply { remove(IndexKey) }
}