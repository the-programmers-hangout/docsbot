package me.aberrantfox.docsbot.services

object DocLookupHelper {
    //note that there is a key called just "strict_mode" as well
    private val languageLookup = mapOf(
            "javascript" to listOf("global_objects/", "operators/", "statements/")
    )

    fun getKeys(language: String, term: String): List<String>? {
        val lowerLanguage = language.toLowerCase()

        if( !(languageLookup.containsKey(lowerLanguage)) ) return null

        return languageLookup[language]!!.map { "${it}$term" }
    }
}