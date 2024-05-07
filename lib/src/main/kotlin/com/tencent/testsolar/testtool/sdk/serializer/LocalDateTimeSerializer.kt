package com.tencent.testsolar.testtool.sdk.serializer

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.format.DateTimeFormatter

// 将LocalDateTime格式化为满足go time规范的格式，否则uniSDK会解析超时
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    // 注意：格式中间的非格式化字符需要用单引号括起来
    //
    // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
    //
    // Any unrecognized letter is an error. Any non-letter character, other than '[', ']', '{', '}', '#'
    // and the single quote will be output directly. Despite this, it is recommended to use single quotes
    // around all characters that you want to output directly to ensure that future changes do not break your application.
    private val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val string = decoder.decodeString()
        val javaLocalDateTime = java.time.LocalDateTime.parse(string, format)
        return LocalDateTime.parse(javaLocalDateTime.toString())
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val string = value.toJavaLocalDateTime().format(format)
        encoder.encodeString(string)
    }

}