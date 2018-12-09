package me.aberrantfox.docsbot.utility

object URLConstants {
    val Resource_Root: String = "https://docs.devdocs.io/"
    val Resource_End: String = "db.json"

    fun languageURL(language: String) = "${URLConstants.Resource_Root}$language/${URLConstants.Resource_End}"
}