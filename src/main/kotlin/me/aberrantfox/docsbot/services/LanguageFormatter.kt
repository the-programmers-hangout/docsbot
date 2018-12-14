package me.aberrantfox.docsbot.services

import net.dv8tion.jda.core.entities.MessageEmbed
import org.reflections.Reflections


interface LanguageFormatter {
    fun format(html: String): MessageEmbed
}

class FormatRepository(formatters: List<Pair<String, LanguageFormatter>>) {
    private val formatterMap: HashMap<String, LanguageFormatter> = HashMap()

    init {
        formatters.forEach { formatterMap[it.first] = it.second }
    }

    fun format(language: String, result: String) = formatterMap[language.toLowerCase()]!!.format(result)

    companion object {
        fun create() = FormatRepository(getFormatters())
    }
}

@Retention(AnnotationRetention.RUNTIME)
annotation class HTMLFormatter(val language: String)

private const val FormatterPath = "me.aberrantfox.docsbot.services.formatters"

private fun getFormatters() =
        Reflections(FormatterPath)
            .getTypesAnnotatedWith(HTMLFormatter::class.java)
            .map { formatterClass ->
                val formatter = formatterClass.constructors.first().newInstance() as LanguageFormatter
                val language = formatterClass.getAnnotation(HTMLFormatter::class.java).language

                Pair(language, formatter)
            }