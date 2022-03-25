package org.vworks.mirai.nightbot.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@ExperimentalSerializationApi
class SerializerIntRange() : KSerializer<SerializableIntRange> {
    override val descriptor: SerialDescriptor = object : SerialDescriptor {
        val names = listOf("start", "endInclusive")
        val indicies = mapOf("start" to 0, "endInclusive" to 1)

        override val elementsCount: Int = 2

        @ExperimentalSerializationApi
        override val kind: SerialKind = SerialKind.CONTEXTUAL

        @ExperimentalSerializationApi
        override val serialName: String = "org.vworks.mirai.nightbot.config.SerializerIntRange\$serialized"

        @ExperimentalSerializationApi
        override fun getElementAnnotations(index: Int): List<Annotation> = Int.serializer().descriptor.getElementAnnotations(index)

        @ExperimentalSerializationApi
        override fun getElementDescriptor(index: Int): SerialDescriptor = Int.serializer().descriptor

        @ExperimentalSerializationApi
        override fun getElementIndex(name: String): Int = indicies[name]?:2

        @ExperimentalSerializationApi
        override fun getElementName(index: Int): String = names[index]

        @ExperimentalSerializationApi
        override fun isElementOptional(index: Int): Boolean = false
    }

    override fun deserialize(decoder: Decoder): SerializableIntRange {
        val range : List<Int> = decoder.decodeString().split("...").map(String::toInt)
        return SerializableIntRange(range[0], range[1])
    }

    override fun serialize(encoder: Encoder, value: SerializableIntRange) {
        encoder.encodeString("${value.start}...${value.endInclusive}")
    }
}