package com.tencent.testsolar.testtool.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoadResult(
    @SerialName("Tests")
    val tests: MutableList<TestCase> = mutableListOf(),

    @SerialName("LoadErrors")
    val loadErrors: MutableList<LoadError> = mutableListOf()
)