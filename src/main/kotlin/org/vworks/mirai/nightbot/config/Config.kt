package org.vworks.mirai.nightbot.config;

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import java.util.*

object Config : AutoSavePluginConfig("config") {
    val doNotUseAllBots : Boolean by value(false)
    val useBotList: List<Long> by value()
    val useWhiteList : Boolean by value(false)
    val useBlackList : Boolean by value(false)
    val whiteList : List<Long> by value(emptyList())
    val blackList : List<Long> by value(emptyList())
    val morningRegEx : List<String> by value(listOf(
        "^早$", "早上好", "早安"
    ))
    val nightRegEx : List<String> by value(listOf(
        "^晚安$", "眠了"
    ))
    val morningPromptsByHour : Map<SerializableIntRange, List<String>>
        by value(mapOf(SerializableIntRange(0, 24) to listOf("早上好！起的真早/真晚啊！")))
    val nightPromptsByHour : Map<SerializableIntRange, List<String>>
        by value(mapOf(SerializableIntRange(0, 24) to listOf("晚安！睡的真早/真晚啊！")))
    val morningPromptsByDuration : Map<SerializableIntRange, List<String>>
        by value(mapOf(SerializableIntRange(0, 24) to listOf("这次一共睡了\$hour!")))
    val nightPromptsByDuration : Map<SerializableIntRange, List<String>>
        by value(mapOf(SerializableIntRange(0, 24) to listOf("今天你清醒了\$hour")))
    val doubleMorningPrompts : Map<SerializableIntRange, List<String>>
        by value(mapOf(SerializableIntRange(0, 24) to listOf("你搁这儿仰卧起坐呢？")))
    val doubleNightPrompts : Map<SerializableIntRange, List<String>>
        by value(mapOf(SerializableIntRange(0, 24) to listOf("你是在梦里又睡了一觉吗？")))

    @Transient var initialized = false
    @Transient var morningRegExCompiled = morningRegEx.map{Regex(it)}
    @Transient var nightRegExCompiled = nightRegEx.map{Regex(it)}

    fun checkLoad() {
        if(!initialized) {
            morningRegExCompiled = morningRegEx.map{ Regex(it) }
            nightRegExCompiled = nightRegEx.map { Regex(it) }
        }
    }
}
