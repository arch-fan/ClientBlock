package com.archfan.clientblock

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import com.archfan.clientblock.ClientBlock.Companion.LOGGER
import kotlinx.serialization.encodeToString

@Serializable
data class Config (
    val action: String = "block",
    val clients: List<String> = emptyList(),
    val kickMessage: String = "The client is not allowed!"
) {
    init {
        require(action in listOf("block", "allow")) {
            "Action must be either 'block' or 'allow'"
        }
    }

    companion object {
        private val json = Json { prettyPrint = true; encodeDefaults = true }
        fun loadFromFile(file: File): Config {
            try {
                if (file.exists()) {
                    val content = file.readText()
                    return json.decodeFromString<Config>(content)
                } else {
                    return Config().also {
                        file.writeText(json.encodeToString(it))
                    }
                }
            } catch (e: Throwable) {
                LOGGER.error(e.message)
                throw e
            }
        }
    }
}