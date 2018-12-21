package me.aberrantfox.docsbot.services

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import me.aberrantfox.docsbot.configuration.BotConfiguration
import me.aberrantfox.docsbot.utility.FileConstants
import org.apache.commons.text.similarity.LevenshteinDistance
import java.io.File
import java.util.concurrent.ConcurrentHashMap

private const val IndexKey = "index"

class DocReader(private val config: BotConfiguration) {
    private val cache: ConcurrentHashMap<String, JsonObject> = ConcurrentHashMap()
    private val threshold = 10000
    private val levenshtein = LevenshteinDistance(threshold)

    private fun findBestMatch(inputs: List<String>, languageObject: JsonObject): String? {
        val parsed: List<Pair<String, Int>> = inputs.map {
            val default = Pair("", 999999)
            languageObject.keySet().fold(default) { acc, key ->
                val item = Pair(key, levenshtein.apply(it, key))
                if (item.second < acc.second && item.second != -1) {
                    item
                } else {
                    acc
                }
            }
        }
        val bestPair = parsed.reduce { acc, it ->
            val current = it.second
            val max = acc.second
            if (current < max) {
                it
            } else {
                acc
            }
        }
        return bestPair.first
    }

    fun lookup(language: String, term: String): String? {
        val lowerLanguage = language.toLowerCase()

        if (!(cache.containsKey(lowerLanguage))) return null

        val keys = DocLookupHelper.getKeys(language, term) ?: return null
        val languageObj = cache[language]!!
        val perfectMatch = keys.firstOrNull { languageObj.has(it) }
        if (!perfectMatch.isNullOrBlank()) {
            return languageObj[perfectMatch].asString
        }

        val best = findBestMatch(keys, languageObj)
        return languageObj[best].asString

//        return languageObj[key].asString
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