package me.aberrantfox.docsbot.services.formatters

import me.aberrantfox.docsbot.services.HTMLFormatter
import me.aberrantfox.docsbot.services.LanguageFormatter
import me.aberrantfox.docsbot.utility.LanguageConstants
import me.aberrantfox.kjdautils.api.dsl.embed
import net.dv8tion.jda.core.entities.MessageEmbed
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

@HTMLFormatter(LanguageConstants.JavaScript)
class JavaScriptFormatter : LanguageFormatter {
    override fun format(html: String): MessageEmbed {
        val body = Jsoup.parse(html).body()

        val code = getCodeExample(body)
        val description = body.select("p").first().text()

        return embed {
            field {
                name = "Description"
                value = description
            }

            field {
                name = "Code"
                value = code
            }
        }
    }

    private fun getCodeExample(body: Element): String {
        val iframe = body.getElementsByClass("interactive-js")

        val code = if(iframe.isNotEmpty()) {
            getIframeCode(iframe .first())
        } else {
            getOtherSampleCode(body)
        }

        return "```js\n$code```"
    }

    private fun getIframeCode(iframe: Element): String {
        val link = iframe.attr("src")
        return Jsoup.connect(link).execute().parse().body().getElementById("static-js").text()
    }

    private fun getOtherSampleCode(body: Element) =
            body.getElementsByAttribute("data-language").first().text()
}