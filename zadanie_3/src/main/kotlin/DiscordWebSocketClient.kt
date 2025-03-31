package com.example

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlin.random.Random

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

    private val categories = mapOf(
        "ludzie" to listOf("Studenci", "Wykładowcy", "Pracownicy"),
        "sale" to listOf("Dydatktyczne", "Wykładowe"),
        "przedmioty" to listOf("Elektronika", "Matematyka", "Fizyka", "Biologia", "Chemia", "Informatyka")
    )

    suspend fun connect() {
        client.webSocket("wss://gateway.discord.gg/?v=10&encoding=json") {
            sendIdentify()
            receiveMessages()
        }
    }

    private suspend fun DefaultClientWebSocketSession.sendIdentify() {
        val identifyPayload = """{
        "op": 2,
        "d": {
            "token": "$token",
            "intents": 33281,
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
                try {
                    val event = jsonParser.decodeFromString<DiscordEvent>(text)
                    if (event.t == "MESSAGE_CREATE") {
                        val content = event.d?.content ?: continue
                        val channelId = event.d.channel_id ?: continue
                        val author = event.d.author ?: continue

                        handleCommand(content, channelId, author.username)
                    }
                } catch (e: Exception) {
                    println("Error parsing message: ${e.message}")
                }
            }
        }
    }

    private suspend fun handleCommand(content: String, channelId: String, author: String) {

        // sprawdź, czy wiadomość to komenda (np. zaczyna się od !)
        if (!content.startsWith("!")) return

        val commandParts = content.substringAfter("!").trim().lowercase().split(" ")
        val command = commandParts[0]
        val argument = commandParts.getOrNull(1)
        println("Command: $command, argument: $argument")


        when (command) {
            "categories" -> {
                val categoryList = categories.keys.joinToString(", ")
                DiscordClient(token).sendMessage(channelId, "Dostępne kategorie: $categoryList")
            }

            "products" -> {
                if (argument == null || !categories.containsKey(argument)) {
                    DiscordClient(token).sendMessage(
                        channelId,
                        "Proszę podać poprawną kategorię. Dostępne: ${categories.keys.joinToString(", ")}"
                    )
                } else {
                    val productList = categories[argument]?.joinToString(", ") ?: "Brak produktów"
                    DiscordClient(token).sendMessage(channelId, "Produkty w kategorii '$argument': $productList")
                }
            }

            else -> {
                DiscordClient(token).sendMessage(channelId, "Ja tego nie znam.")
            }
        }
    }
}