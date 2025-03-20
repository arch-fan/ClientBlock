package com.archfan.clientblock.mixins;

import com.archfan.clientblock.Config;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.network.packet.BrandCustomPayload;
import com.archfan.clientblock.ClientBlock;
import com.archfan.clientblock.ClientHandler;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.DisconnectionInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerCommonNetworkHandler.class)
public abstract class ServerCommonNetworkHandlerMixin {

    @Shadow
    protected abstract GameProfile getProfile();

    @Shadow
    public abstract void disconnect(Text reason);

    @Inject(at = @At("HEAD"), method = "onDisconnected")
    private void onDisconnected(DisconnectionInfo info, CallbackInfo cb) {
        ClientHandler.INSTANCE.getMapOfClients().remove(this.getProfile().getName());
    }

    @Inject(at = @At("HEAD"), method = "onCustomPayload", cancellable = true)
    private void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo info) {
        if (packet.payload() instanceof BrandCustomPayload(String brand)) {
            String playerName = this.getProfile().getName();

            Config config = ClientBlock.Companion.getCONFIG();
            boolean logger = config.getLogger();
            String action = config.getAction();
            List<String> clients = config.getClients();
            String kickMessage = config.getKickMessage();
            List<String> whitelist = config.getWhitelist();

            if (logger) {
                ClientBlock.Companion.getLOGGER().info("Player {} connecting with client {}", playerName, brand);
            }

            boolean shouldKick = clients.contains(brand);

            if ("allow".equals(action)) {
                shouldKick = !shouldKick;
            }

            if (whitelist.contains(playerName)) {
                shouldKick = false;
            }

            if (shouldKick) {
                this.disconnect(Text.of(kickMessage));
                ClientBlock.Companion.getLOGGER().info("Blocked {} using client {}", playerName, brand);
                info.cancel();
            } else {
                ClientHandler.INSTANCE.getMapOfClients().put(playerName, brand);
            }
        }
    }
}
