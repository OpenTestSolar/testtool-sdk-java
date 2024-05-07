package com.tencent.testsolar.testtool.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LoadError(
    @SerialName("Name")
    val name: String,

    @SerialName("Message")
    val message: String
) {
    init {
        require(name.isNotBlank()) { "LoadError.name must not be black" }
        require(message.isNotBlank()) { "LoadError.message must not be black" }
    }
}