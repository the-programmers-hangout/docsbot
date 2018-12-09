package me.tph.docsbot

import me.aberrantfox.kjdautils.api.dsl.PrefixDeleteMode
import me.aberrantfox.kjdautils.api.startBot
data class MyCustomBotConfiguration(val version: String, val token: String)


fun main(args: Array<String>) {
    startBot(token) {
        val path = "me.tph.docsbot"
        val eprefix = "!"
        val myConfig = MyCustomBotConfiguration("0.1.0", token)

        registerInjectionObject(myConfig)
        configure {
            prefix=eprefix
            commandPath=path
            deleteMode=PrefixDeleteMode.None
        }
    }
}
