package com.archfan.clientblock

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object ClientHandler : Command {
    // Map saving <Player Name, Client>
    val mapOfClients = mutableMapOf<String, String>()

    override fun dispatcher(ctx: CommandContext<ServerCommandSource>) {
        ctx.source.sendFeedback({
            Text.literal(output())
        },
        false
        )
    }

    override fun output(): String = """
        §c§lClientBlock§r - List of §c${mapOfClients.size}§r clients:
        ${mapOfClients.entries.joinToString("\n") { "§b${it.key}:§r §c§n${it.value}§r" }}
    """.trimIndent()
}