package com.example

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

@Serializable
data class DiscordEvent(
    val t: String? = null,
    val s: Int? = null,
    val op: Int,
    val d: DiscordMessageData? = null
)

@Serializable
data class DiscordMessageData(
    val content: String? = null,
    val channel_id: String? = null,
    val author: DiscordAuthor? = null
)

@Serializable
data class DiscordAuthor(
    val username: String,
    val id: String,
    val bot: Boolean = false
)




class DiscordWebSocketClient(private val token: String) {
    private val client = HttpClient {
        install(WebSockets)
    }

    suspend fun connect() {
        client.webSocket("wss://gateway.discord.gg/?v=10&encoding=json") {
            launch { receiveMessages() }
            sendIdentify()
        }
    }

    private suspend fun DefaultClientWebSocketSession.sendIdentify() {
        val identifyPayload = """{
            "op": 2,
            "d": {
                "token": "$token",
                "intents": 513,
                "properties": {
                    "os": "linux",
                    "browser": "ktor-client",
                    "device": "ktor-client"
                }
            }
        }"""
        send(identifyPayload)
    }

    private suspend fun DefaultClientWebSocketSession.receiveMessages() {
        val jsonParser = Json { ignoreUnknownKeys = true }

        for (frame in incoming) {
            if (frame is Frame.Text) {
                val text = frame.readText()
                println("Received frame: $text")

                try {
                    val event = jsonParser.decodeFromString<DiscordEvent>(text)
                    if (event.t == "MESSAGE_CREATE") {
                        val content = event.d?.content ?: continue
                        val channelId = event.d.channel_id ?: continue
                        val author = event.d.author ?: continue
                        println("Message received: $content")
                        println("Channel ID: $channelId")
                        println("Author: ${author.username}")
                    }
                } catch (e: Exception) {
                    println("Error parsing message: ${e.message}")
                }
            }
        }
    }
    // Przykładowa obsługa komend
    private suspend fun handleCommand(content: String, channelId: String) {
        // sprawdź, czy wiadomość to komenda (np. zaczyna się od !)
        if (!content.startsWith("!")) return

        when(content.substringAfter("!").trim().lowercase()) {
            "ping" -> {
                DiscordClient(token).sendMessage(channelId, "pong!")
            }
            "hello" -> {
                DiscordClient(token).sendMessage(channelId, "Hello! 👋")
            }
            else -> {
                DiscordClient(token).sendMessage(channelId, "Nieznana komenda!")
            }
        }
    }
}