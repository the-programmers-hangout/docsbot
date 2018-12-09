package me.tph.docsbot

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import me.aberrantfox.kjdautils.api.dsl.embed
import net.dv8tion.jda.core.entities.EmbedType
import net.dv8tion.jda.core.entities.MessageEmbed
import java.io.File
import java.lang.Exception
import java.net.URL

typealias Documentation = LinkedTreeMap<*, *>;

var docsCache = HashMap<String, Documentation>()
val gson = Gson()

data class DocsResponse(val title: String, val body: String, val example: String?)

data class DocsRequest(
    val detailed: Boolean,
    val input: String,
    val docs: Documentation
)

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

fun createDocs(language: String, handler: (DocsRequest) -> DocsResponse): (input: String) -> MessageEmbed {
    return {
        val docs = fetchDocs(language)
        val request = DocsRequest(
            input = it,
            docs = docs,
            detailed = true
        )
        val out = handler(request)
        val codeblock = "```$language\n${out.example}\n```"
        embed {
            title("**${out.title}**")
            description("${out.body}\n$codeblock")
        }
    }
}