package me.tph.docsbot

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.MessageEmbed
import java.io.File
import java.net.URL

typealias Documentation = LinkedTreeMap<*, *>;

var docsCache = HashMap<String, Documentation>()
val gson = Gson()

data class DocsResponse(val title: String, val body: String, val example: String?, val link: String)

class DocsConfig {
    var icon: String? = ""
    var execute: (input: DocsRequest) -> DocsResponse = {
        DocsResponse(
            title = "",
            body = "",
            example = "",
            link = ""
        )
    }
}

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

fun createDocs(language: String, construct: DocsConfig.() -> Unit): (input: String) -> MessageEmbed {
    return {
        val config = DocsConfig()
        config.construct()
        val docs = fetchDocs(language)
        val request = DocsRequest(
            input = it,
            docs = docs,
            detailed = true
        )
        val out = config.execute(request)
        val codeblock = "```$language\n${out.example}\n```"
        val embed = EmbedBuilder()
            .setDescription("${out.body}\n$codeblock")
        if (!config.icon.isNullOrEmpty()) {
            embed.setAuthor(out.title, config.icon, config.icon).build()
        } else {
            embed.setAuthor(out.title).build()
        }
    }
}