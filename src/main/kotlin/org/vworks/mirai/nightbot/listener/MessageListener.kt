package org.vworks.mirai.nightbot.listener

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.subscribeMessages
import org.vworks.mirai.nightbot.config.Config
import org.vworks.mirai.nightbot.data.RegimeEvent
import org.vworks.mirai.nightbot.data.RegimenData
import kotlinx.datetime.LocalDateTime
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.content
import kotlin.time.Duration
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

class MessageListener(val pluginBase: KotlinPlugin) : ListenerHost {

    @ExperimentalTime
    @EventHandler
    suspend fun MessageEvent.onMessage() {
        if (Config.doNotUseAllBots && bot.id !in Config.useBotList) {
            return
        }
        if (Config.useWhiteList && sender.id !in Config.whiteList &&
            if (sender is Member) (sender as Member).group.id !in Config.whiteList else true
        ) {
            return
        }
        if (Config.useBlackList && (sender.id in Config.blackList) ||
            if (sender is Member) ((sender as Member).group.id) in Config.blackList else false
        ) {
            return
        }

        matchMorning()
        matchNight()
    }

    @ExperimentalTime
    private suspend fun MessageEvent.matchMorning() {
        Config.checkLoad()
        Config.morningRegExCompiled.forEach {
            if (it.containsMatchIn(message.contentToString())) {
                val randTimeGreeting = Config.morningPromptsByHour
                    .filter { it2 -> Calendar.getInstance().get(Calendar.HOUR_OF_DAY) in it2.key }
                    .flatMap { it2 -> it2.value }.randomOrNull() ?: ""

                val combinedDurationString = when {
                    RegimenData.lastEvent[sender.id] == RegimeEvent.WAKE -> {
                        val duration =
                            Duration.seconds((Clock.System.now().epochSeconds - RegimenData.lastAwakeTime[sender.id]!!.epochSeconds))
                        val readableDuration =
                            if (duration.toHours() >= 24) "${duration.toDays()}天${duration.toHoursPart()}小时"
                            else "${duration.toHoursPart()}小时${duration.toMinutesPart()}分"
                        Config.doubleMorningPrompts
                            .filter { it2 -> duration.toHours() in it2.key }
                            .flatMap { it2 -> it2.value }.randomOrNull()?.replace("\$hour", readableDuration) ?: ""
                    }
                    RegimenData.lastSleepingTime.containsKey(sender.id) -> {
                        val sleptDuration =
                            Duration.seconds((Clock.System.now().epochSeconds - RegimenData.lastSleepingTime[sender.id]!!.epochSeconds))
                        val randDurationGreeting = Config.morningPromptsByDuration
                            .filter { it2 -> sleptDuration.toHours() in it2.key }
                            .flatMap { it2 -> it2.value }.randomOrNull() ?: ""
                        val readableDuration =
                            if (sleptDuration.toHours() >= 24) "${sleptDuration.toDays()}天${sleptDuration.toHoursPart()}小时"
                            else "${sleptDuration.toHoursPart()}小时${sleptDuration.toMinutesPart()}分"
                        randDurationGreeting.replace("\$hour", readableDuration)
                    }
                    else -> ""
                }

                this.subject.sendMessage(randTimeGreeting + combinedDurationString)
                RegimenData.lastAwakeTime[sender.id] = Clock.System.now()
                RegimenData.lastEvent[sender.id] = RegimeEvent.WAKE

                return@matchMorning
            }
        }
    }

    @ExperimentalTime
    private suspend fun MessageEvent.matchNight() {
        Config.checkLoad()
        Config.nightRegExCompiled.forEach {
            if (it.containsMatchIn(message.contentToString())) {
                val randTimeGreeting = Config.nightPromptsByHour
                    .filter { it2 -> Calendar.getInstance().get(Calendar.HOUR_OF_DAY) in it2.key }
                    .flatMap { it2 -> it2.value }.randomOrNull() ?: ""

                val combinedDurationString = when {
                    RegimenData.lastEvent[sender.id] == RegimeEvent.SLEEP -> {
                        val duration =
                            Duration.seconds((Clock.System.now().epochSeconds - RegimenData.lastSleepingTime[sender.id]!!.epochSeconds))
                        val readableDuration =
                            if (duration.toHours() >= 24) "${duration.toDays()}天${duration.toHoursPart()}小时"
                            else "${duration.toHoursPart()}小时${duration.toMinutesPart()}分"
                        Config.doubleNightPrompts
                            .filter { it2 -> duration.toInt(DurationUnit.HOURS) in it2.key }
                            .flatMap { it2 -> it2.value }.randomOrNull()?.replace("\$hour", readableDuration) ?: ""
                    }
                    RegimenData.lastAwakeTime.containsKey(sender.id) -> {
                        val awakeDuration =
                            Duration.seconds((Clock.System.now().epochSeconds - RegimenData.lastAwakeTime[sender.id]!!.epochSeconds))
                        val randDurationGreeting = Config.nightPromptsByDuration
                            .filter { it2 -> awakeDuration.toHours() in it2.key }
                            .flatMap { it2 -> it2.value }.randomOrNull() ?: ""
                        val readableDuration =
                            if (awakeDuration.toHours() >= 24) "${awakeDuration.toDays()}天${awakeDuration.toHoursPart()}小时"
                            else "${awakeDuration.toHoursPart()}小时${awakeDuration.toMinutesPart()}分"
                        randDurationGreeting.replace("\$hour", readableDuration)
                    }
                    else -> ""
                }

                this.subject.sendMessage(randTimeGreeting + combinedDurationString)
                RegimenData.lastSleepingTime[sender.id] = Clock.System.now()
                RegimenData.lastEvent[sender.id] = RegimeEvent.SLEEP

                return@matchNight
            }
        }
    }

    @ExperimentalTime
    private fun Duration.toHours() = this.toInt(DurationUnit.HOURS)

    @ExperimentalTime
    private fun Duration.toHoursPart() = this.toInt(DurationUnit.HOURS) % 24

    @ExperimentalTime
    private fun Duration.toDays() = this.toInt(DurationUnit.DAYS)

    @ExperimentalTime
    private fun Duration.toMinutesPart() = this.toInt(DurationUnit.MINUTES) % 60
}