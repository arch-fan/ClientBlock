package com.archfan.clientblock

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.BrandCustomPayload
import net.minecraft.network.packet.CustomPayload
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object ClientBlockEventHandlers {

    fun registerEvents() {
        ServerPlayConnectionEvents.DISCONNECT.register(ServerPlayConnectionEvents.Disconnect { handler, server ->
            val player = handler.player
            val playerName = player.gameProfile.name
            ClientHandler.mapOfClients.remove(playerName)
        })

        ServerPlayNetworking.registerGlobalReceiver(BrandCustomPayload.ID) { payload, context ->
            val player = context.player()
            val playerName = player.gameProfile.name
            val brand = payload.brand
            val action = ClientBlock.CONFIG.action
            val clients = ClientBlock.CONFIG.clients
            val kickMessage = ClientBlock.CONFIG.kickMessage
            val logger = ClientBlock.CONFIG.logger

            if (logger) {
                ClientBlock.LOGGER.info("Player $playerName connecting with client $brand")
            }

            var shouldKick = clients.contains(brand)

            if (action == "allow") {
                shouldKick = !shouldKick
            }

            if (shouldKick) {
                player.networkHandler.disconnect(Text.of(kickMessage))
                ClientBlock.LOGGER.info("Blocked $playerName using client $brand")
            } else {
                ClientHandler.mapOfClients[playerName] = brand
            }
        }
    }
}
