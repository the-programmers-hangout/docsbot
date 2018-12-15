package me.aberrantfox.docsbot.configuration

import com.google.gson.GsonBuilder
import me.aberrantfox.docsbot.utility.FileConstants
import java.io.File


data class BotConfiguration (
    val prefix: String = "!",
    val languages: Set<String> = HashSet()
)

private val gson = GsonBuilder().setPrettyPrinting().create()

fun loadConfig(root: String): BotConfiguration? {
    val file = getFile(root)

    if (file.exists()) return gson.fromJson(file.readText(), BotConfiguration::class.java)

    saveConfig(BotConfiguration(), root)
    return null
}

fun saveConfig(globalConfiguration: BotConfiguration, root: String) = getFile(root).writeText(gson.toJson(globalConfiguration))

private val path = "${File.separator}${FileConstants.Configuration_Directory}${File.separator}"

private fun getFile(root: String) = File("$root${path}config.json")