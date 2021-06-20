package org.vworks.mirai.nightbot.data

import kotlinx.serialization.Serializable

@Serializable
enum class RegimeEvent {
    WAKE, SLEEP
}