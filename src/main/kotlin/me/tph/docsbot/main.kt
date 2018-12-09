package me.tph.docsbot
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.net.URL

val gson = Gson()
fun readFile(name: String) = File(name).readText()

fun fetchJsData() = gson.fromJson(readFile("src/main/resources/database/js.json"), LinkedTreeMap::class.java)

fun extractCodeblock(soup: Document) = soup
    .select("pre[data-language=\"js\"]")
    .first()
    ?.text()

fun extractExample(soup: Document): String? = soup.select("iframe.interactive-js").attr("src")

fun queryiFrameExample(src: String): String? {
    val content = URL(src).readText()
    val soup = Jsoup.parse(content)
    return soup.select("#static-js").text()
}

fun fetchRelevantCodeblock(soup: Document): String? {
    val best = extractExample(soup)
    return if (best.isNullOrBlank()) {
        extractCodeblock(soup)
    } else {
        queryiFrameExample(best)
    }
}

fun fetch(method: String): String? {
    val items = fetchJsData()
    val x: String = items["global_objects/$method"].toString()
    val soup = Jsoup.parse(x)
    return fetchRelevantCodeblock(soup)
}
