package org.vworks.mirai.nightbot

import net.mamoe.mirai.console.command.CommandManager.INSTANCE.registerCommand
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.registeredCommands
import net.mamoe.mirai.console.events.ConsoleEvent
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.info
import org.vworks.mirai.nightbot.command.Commands
import org.vworks.mirai.nightbot.config.Config
import org.vworks.mirai.nightbot.data.RegimenData
import org.vworks.mirai.nightbot.listener.MessageListener

object Nightbot : KotlinPlugin(
    JvmPluginDescription(
        id = "org.vworks.mirai.nightbot",
        name = "Nightbot - Mirai",
        version = "1.1.1",
    ) {
        author("MetricVoid")
        info("""早安晚安bot""")
    }
) {
    override fun onEnable() {
        logger.info { "Loading config file." }
        Config.reload()

        logger.info { "Loading data." }
        RegimenData.reload()

        logger.info { "Starting event listener." }
        val msgListener = MessageListener(this)
        GlobalEventChannel.registerListenerHost(msgListener)

        logger.info { "Initialization complete." }

        registerCommand(Commands)
    }
}