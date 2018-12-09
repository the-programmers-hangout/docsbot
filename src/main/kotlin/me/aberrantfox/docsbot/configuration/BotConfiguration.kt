package me.aberrantfox.docsbot.configuration

import com.google.gson.GsonBuilder
import me.aberrantfox.docsbot.utility.FileConstants
import java.io.File


data class BotConfiguration (
    val prefix: String = "!",
    val languages: Set<String> = HashSet()
)

private val gson = GsonBuilder().setPrettyPrinting().create()
private val file = File("${FileConstants.Configuration_Directory}config.json")

fun loadConfig() =
        if (file.exists()) {
            gson.fromJson(file.readText(), BotConfiguration::class.java)
        } else {
            saveConfig(BotConfiguration())
            null
        }

fun saveConfig(globalConfiguration: BotConfiguration) = file.writeText(gson.toJson(globalConfiguration))
