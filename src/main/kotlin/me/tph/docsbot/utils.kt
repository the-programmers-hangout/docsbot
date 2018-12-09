package me.tph.docsbot

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import java.io.File
import java.lang.Exception
import java.net.URL

typealias Documentation = LinkedTreeMap<*, *>;
var docsCache = HashMap<String, Documentation>()
val gson = Gson()

data class DocsResponse(val body: String, val example: String?)

interface DocsHook {
    fun isValid(input: String): Boolean
    fun fetch(input: String): DocsResponse
}

fun toDocs(content: String): Documentation = gson.fromJson(content, LinkedTreeMap::class.java)

/**
 * Fetches documentation from either
 * 1. Memory
 * 2. Existing file
 * 3. Devdocs url
 */
fun fetchDocs(language: String): LinkedTreeMap<*, *> {
    val existing = docsCache[language]

    if (!existing.isNullOrEmpty()) {
        return existing
    }

    val pathName = "src/main/resources/database/$language.json"
    val file = File(pathName)

    if (file.exists()) {
        val parsed = toDocs(file.readText())
        docsCache[language] = parsed
        return parsed
    }


    // Letting it crash at this point, if commands aren't working it shouldn't be a "whoops"
    val response = URL("https://docs.devdocs.io/$language/db.json").readText()

    File(pathName).writeText(response)
    val parsed = toDocs(response)
    docsCache[language] = parsed

    return parsed
}

fun createDocs(language: String, handler: (docs: Documentation) -> DocsResponse): (input: String) -> String {
    return {
        val docs = fetchDocs(language)
        val (body, example) = handler(docs)
        body // something like that
    }
}