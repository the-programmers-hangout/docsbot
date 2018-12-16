package me.aberrantfox.docsbot.configuration

import me.aberrantfox.docsbot.utility.FileConstants
import java.io.File

data class DirectoryConfiguration(val root: String) {
    val documentationDirectory = File(rootDir(FileConstants.Documentation_Directory)).apply { mkdir() }

    fun dbFilePath(language: String) = "${rootDir(FileConstants.Documentation_Directory)}$language.json"

    private fun rootDir(dir: String) = "$root${File.separator}$dir${File.separator}"
}