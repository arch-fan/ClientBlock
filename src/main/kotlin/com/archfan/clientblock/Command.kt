package com.archfan.clientblock

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource

interface Command {
    fun dispatcher(ctx: CommandContext<ServerCommandSource>)
    fun output(): String
}