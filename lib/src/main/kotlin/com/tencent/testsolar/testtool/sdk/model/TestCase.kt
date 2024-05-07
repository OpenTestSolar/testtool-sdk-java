package com.tencent.testsolar.testtool.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestCase(
    @SerialName("Name")
    val name: String,

    @SerialName("Attributes")
    val attributes: HashMap<String, String>
)