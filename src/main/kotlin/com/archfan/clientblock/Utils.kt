package com.archfan.clientblock

import net.fabricmc.loader.api.FabricLoader

fun getModVersion(): String = FabricLoader
    .getInstance()
    .getModContainer("clientblock")
    .map { it.metadata.version.friendlyString }
    .orElse("main")