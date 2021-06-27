package org.vworks.mirai.nightbot.command

import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.ConsoleCommandSender
import net.mamoe.mirai.console.plugin.jvm.reloadPluginConfig
import org.vworks.mirai.nightbot.Nightbot
import org.vworks.mirai.nightbot.config.Config

object Commands : CompositeCommand(
    Nightbot, "nightbot",
) {
    @SubCommand
    suspend fun ConsoleCommandSender.reload() {
        Nightbot.reloadPluginConfig(Config)
        Nightbot.logger.info("Configuration files reloaded.")
    }
}