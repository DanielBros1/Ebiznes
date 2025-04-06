package com.example.telegram

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.util.*

fun TelegramMain() {
    val (botToken, botUsername) = loadBotConfig()
    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    try {
        botsApi.registerBot(TelegramBot(botToken, botUsername))
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}

fun loadBotConfig(): Pair<String, String> {
    val props = Properties()
    val inputStream = ClassLoader.getSystemResourceAsStream("config.properties")

    if (inputStream != null) {
        props.load(inputStream)
        val botToken = props.getProperty("botToken") ?: error("Brak TELEGRAM_TOKEN!")
        val botUsername = props.getProperty("botUsername") ?: error("Brak TELEGRAM_USERNAME!")
        return Pair(botToken, botUsername)
    } else {
        throw IllegalStateException("Nie można znaleźć pliku konfiguracyjnego.")
    }
}