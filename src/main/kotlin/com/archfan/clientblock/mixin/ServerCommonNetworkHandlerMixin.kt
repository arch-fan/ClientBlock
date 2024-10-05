package com.archfan.clientblock.mixin

import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket
import net.minecraft.server.network.ServerCommonNetworkHandler
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.Inject
import net.minecraft.network.packet.BrandCustomPayload

import com.archfan.clientblock.ClientBlock.Companion.LOGGER
import com.archfan.clientblock.ClientBlock.Companion.CONFIG
import com.mojang.authlib.GameProfile
import net.minecraft.text.Text
import org.spongepowered.asm.mixin.Shadow

import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Suppress("NonJavaMixin")
@Mixin(ServerCommonNetworkHandler::class)
abstract class ServerCommonNetworkHandlerMixin {

    @Shadow
    abstract fun getProfile(): GameProfile

    @Shadow
    abstract fun disconnect(reason: Text)

    @Inject(at = [At("HEAD")], method = ["onCustomPayload"], cancellable = true)
    private fun onCustomPayload(packet: CustomPayloadC2SPacket, info: CallbackInfo) {
        val payload = packet.payload()

        if(payload is BrandCustomPayload) {
            val brand = payload.brand()
            val (action, clients, kickMessage) = CONFIG

            var shouldKick: Boolean = clients.contains(brand)

            if(action == "allow") {
                shouldKick = !shouldKick
            }

            LOGGER.info("Blocked ${this.getProfile().name} using client $brand")

            if (shouldKick) {
                this.disconnect(Text.of(kickMessage))
                info.cancel()
            }
        }
    }
}