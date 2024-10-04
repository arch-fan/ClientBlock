package com.archfan.clientblock

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import com.archfan.clientblock.ClientBlock.Companion.LOGGER

@Serializable
data class Config (
    val action: String = "block",
    val clients: Array<String>,
    val kickMessage: String = "The client is not allowed!"
) {
    init {
        require(action in listOf("block", "allow")) {
            "Action must be either 'block' or 'allow'"
        }
    }

    companion object {
        fun loadFromFile(file: File): Config {
            try {
                val content = file.readText()
                return Json.decodeFromString<Config>(content)
            } catch (e: Throwable) {
                LOGGER.error(e.message)
                throw e
            }
        }
    }
}