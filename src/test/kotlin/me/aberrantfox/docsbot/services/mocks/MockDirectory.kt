package me.aberrantfox.docsbot.services.mocks

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import me.aberrantfox.docsbot.configuration.BotConfiguration
import me.aberrantfox.docsbot.configuration.DirectoryConfiguration
import me.aberrantfox.docsbot.utility.LanguageConstants
import org.jsoup.Jsoup
import java.io.File

const val prefix = "!"
const val javaText = "{ text }"
const val javaScriptText = "{ otherText }"
const val javaPath = "java.json"
const val javaScriptPath = "javascript.json"


val configMock = mock<BotConfiguration> {
    on { languages } doReturn setOf(LanguageConstants.JavaScript, LanguageConstants.Java)
    on { prefix } doReturn prefix
}

val fileMockJava = mock<File> {
    on { name } doReturn LanguageConstants.Java
    on { writeText(javaText) } doReturn Unit
}

val fileMockJavaScript = mock<File> {
    on { name } doReturn LanguageConstants.JavaScript
    on { writeText(javaScriptText) } doReturn Unit
}

val fileMocks = arrayOf(fileMockJava, fileMockJavaScript)

val docDirMock = mock<File> {
    on { mkdir() } doReturn true
    on { listFiles() } doReturn fileMocks
}

val dirConfigMock = mock<DirectoryConfiguration> {
    on { documentationDirectory } doReturn docDirMock
    on { dbFilePath(LanguageConstants.Java) } doReturn javaPath
    on { dbFilePath(LanguageConstants.JavaScript) } doReturn javaScriptPath
}