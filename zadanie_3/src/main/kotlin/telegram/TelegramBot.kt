package com.example.telegram

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


class TelegramBot(private val botToken: String, private val botUsername: String) : TelegramLongPollingBot(botToken) {
    override fun getBotUsername(): String {
        return botUsername
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId.toString()

            val responseText = when (messageText.lowercase()) {
                "/start" -> "Witaj! "
                "jak sie masz?" -> "Dziekuje, mam się dobrze!"
                else -> "Nie rozumiem polecenia."
            }

            val message = SendMessage()
            message.chatId = chatId
            message.text = responseText

            try {
                execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
        }
    }

}
