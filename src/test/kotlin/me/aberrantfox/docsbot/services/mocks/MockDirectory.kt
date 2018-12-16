package me.aberrantfox.docsbot.services.mocks


import io.mockk.every
import io.mockk.mockk
import me.aberrantfox.docsbot.configuration.BotConfiguration
import me.aberrantfox.docsbot.configuration.DirectoryConfiguration
import me.aberrantfox.docsbot.utility.LanguageConstants
import me.aberrantfox.docsbot.utility.URLConstants
import org.jsoup.Connection
import org.jsoup.Jsoup.connect
import java.io.File

const val prefixDefault = "!"
const val javaText = "{ text }"
const val javaScriptText = "{ otherText }"
const val javaPath = "java.json"
const val javaScriptPath = "javascript.json"
const val sampleBody = "<body></body>"


val configMock = mockk<BotConfiguration> {
    every { languages } returns setOf(LanguageConstants.JavaScript, LanguageConstants.Java)
    every { prefix } returns prefixDefault
}

val fileMockJava = mockk<File> {
    every { name } returns LanguageConstants.Java
    every { writeText(javaText) } returns Unit
}

val fileMockJavaScript = mockk<File> {
    every { name } returns LanguageConstants.JavaScript
    every { writeText(javaScriptText) } returns Unit
}

val fileMocks = arrayOf(fileMockJava, fileMockJavaScript)

val docDirMock = mockk<File> {
    every { mkdir() } returns true
    every { listFiles() } returns fileMocks
}

val dirConfigMock = mockk<DirectoryConfiguration> {
    every { documentationDirectory } returns docDirMock
    every { dbFilePath(LanguageConstants.Java) } returns javaPath
    every { dbFilePath(LanguageConstants.JavaScript) } returns javaScriptPath
}

val connectionResponseMock = mockk<Connection.Response> {
    every { body() } returns sampleBody
}

val connectionMock = mockk<Connection> {
    every { connect(URLConstants.languageURL(LanguageConstants.Java)) } returns this
    every { maxBodySize(0) } returns this
    every { timeout(0) } returns this
    every { ignoreContentType(true) } returns this
    every { execute() } returns connectionResponseMock
}

