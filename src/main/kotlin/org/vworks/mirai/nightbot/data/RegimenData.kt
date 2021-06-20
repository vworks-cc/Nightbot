package org.vworks.mirai.nightbot.data

import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import kotlinx.datetime.LocalDateTime

object RegimenData : AutoSavePluginData("regimen") {
    val lastEvent : MutableMap<Long, RegimeEvent> by value()
    val lastSleepingTime : MutableMap<Long, Instant> by value()
    val lastAwakeTime : MutableMap<Long, Instant> by value()
}