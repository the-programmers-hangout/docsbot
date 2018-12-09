package me.aberrantfox.docsbot.services.formatters

import me.aberrantfox.kjdautils.api.dsl.embed
import net.dv8tion.jda.core.entities.MessageEmbed
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class JavaScriptFormatter {
    fun format(html: String): MessageEmbed {
        val body = Jsoup.parse(html).body()
        val iframe = body.getElementsByClass("interactive-js").first()

        val code = getInteractiveJS(iframe)
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

    private fun getInteractiveJS(iframe: Element): String {
        val link = iframe.attr("src")
        val code = Jsoup.connect(link).execute().parse().body().getElementById("static-js").text()

        return "```js\n$code```"
    }
}