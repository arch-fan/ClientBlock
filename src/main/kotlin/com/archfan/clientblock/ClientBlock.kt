package com.archfan.clientblock

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory


class ClientBlock : ModInitializer {
    companion object {
        val LOGGER = LoggerFactory.getLogger("ClientBlock")
        val CONFIG = Config.loadFromFile(
            FabricLoader
                .getInstance()
                .configDir
                .resolve("clientblock.json")
                .toFile()
        )
    }

    override fun onInitialize() {
        LOGGER.info("Mod Loaded. $CONFIG")
    }
}

