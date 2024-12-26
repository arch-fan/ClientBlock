package com.archfan.clientblock

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.network.packet.BrandCustomPayload
import net.minecraft.network.packet.CustomPayload
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class ClientBlock : ModInitializer {
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger("ClientBlock")
        val CONFIG = Config.loadFromFile(
            FabricLoader
                .getInstance()
                .configDir
                .resolve("clientblock.json")
                .toFile()
        )
    }

    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandHandler::register)

        LOGGER.info("Mod Loaded. $CONFIG")
    }
}

