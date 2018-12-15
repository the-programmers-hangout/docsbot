package me.aberrantfox.docsbot.configuration

import me.aberrantfox.docsbot.utility.FileConstants
import java.io.File

data class DirectoryConfiguration(val root: String) {
    val projectDirectory = File(root)
    val documentationDirectory = File(rootDir(FileConstants.Documentation_Directory))
    val configurationDirectory = File(rootDir(FileConstants.Configuration_Directory))

    init {
        projectDirectory.mkdir()
        documentationDirectory.mkdir()
        configurationDirectory.mkdir()
    }

    fun dbFilePath(language: String) = "${FileConstants.Documentation_Directory}$language.json"

    private fun rootDir(dir: String) = "$root${File.separator}$dir"
}