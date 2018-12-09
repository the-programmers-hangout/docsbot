package me.aberrantfox.docsbot.utility

import java.io.File

object FileConstants {
    val Project_Directory: String = "docbot${File.separator}"
    val Documentation_Directory: String = "$Project_Directory${File.separator}docs${File.separator}"
    val Configuration_Directory: String = "$Project_Directory${File.separator}config${File.separator}"

    val Project_Directory_File: File = File(Project_Directory)
    val Documentation_Directory_File: File = File(Documentation_Directory)
    val Configuration_Directory_File: File = File(Configuration_Directory)

    init {
        Project_Directory_File.mkdir()
        Documentation_Directory_File.mkdir()
        Configuration_Directory_File.mkdir()
    }

    fun dbFilePath(language: String) = "${FileConstants.Documentation_Directory}$language.json"
}