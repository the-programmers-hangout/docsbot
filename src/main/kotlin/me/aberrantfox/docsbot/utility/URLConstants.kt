package me.aberrantfox.docsbot.utility

object URLConstants {
    const val Resource_Root: String = "https://docs.devdocs.io/"
    const val Resource_End: String = "db.json"

    fun languageURL(language: String) = "${URLConstants.Resource_Root}$language/${URLConstants.Resource_End}"
}