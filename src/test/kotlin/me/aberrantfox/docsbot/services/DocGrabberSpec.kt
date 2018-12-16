package me.aberrantfox.docsbot.services


import io.mockk.every
import io.mockk.mockkStatic
import me.aberrantfox.docsbot.services.mocks.connectionMock
import me.aberrantfox.docsbot.services.mocks.configMock
import me.aberrantfox.docsbot.services.mocks.dirConfigMock
import me.aberrantfox.docsbot.services.mocks.sampleBody
import me.aberrantfox.docsbot.utility.LanguageConstants
import me.aberrantfox.docsbot.utility.URLConstants
import org.jsoup.Jsoup
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.io.File
import kotlin.test.assertEquals


object DocGrabberSpec : Spek({
    Feature("Doc access over HTTP for setting up and updating documentation") {
        mockkStatic("org.jsoup.Jsoup") {
            every { Jsoup.connect(URLConstants.languageURL(LanguageConstants.Java)) } returns connectionMock
            every { Jsoup.connect(URLConstants.languageURL(LanguageConstants.JavaScript)) } returns connectionMock
        }

        Scenario("There are no docs, and a user wants to setup a docsbot instance") {
            val docGrabber = DocGrabber(configMock, dirConfigMock)

            When("All of the documentation is pulled") {
                docGrabber.pullAllDocs()
            }

            Then("The documentation can be found at the selected location on disk") {
                val text = File(dirConfigMock.dbFilePath(LanguageConstants.Java)).readText()
                assertEquals(text, sampleBody)
            }
        }
    }
})