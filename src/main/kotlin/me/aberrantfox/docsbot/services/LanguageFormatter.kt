package me.aberrantfox.docsbot.services

import me.aberrantfox.docsbot.services.formatters.JavaScriptFormatter
import me.aberrantfox.kjdautils.api.dsl.embed

class LanguageFormatter {
    fun format(language: String, result: String) = JavaScriptFormatter().format(result)
}