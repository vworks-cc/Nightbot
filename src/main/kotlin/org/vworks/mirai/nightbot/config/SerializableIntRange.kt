package org.vworks.mirai.nightbot.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = SerializerIntRange::class)
@SerialName("IntRange")
class SerializableIntRange(
    override val start : Int,
    override val endInclusive : Int,
) : java.io.Serializable, ClosedRange<Int>, Iterable<Int> {

    constructor(range : IntRange) : this(range.first, range.last)

    fun getIntRange() : IntRange = start..endInclusive

    override fun iterator(): Iterator<Int> = object : Iterator<Int> {
        var current = start

        override fun hasNext(): Boolean {
            return current <= endInclusive
        }

        override fun next(): Int = current++
    }
}