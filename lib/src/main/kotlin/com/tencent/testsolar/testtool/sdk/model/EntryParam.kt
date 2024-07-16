package com.tencent.testsolar.testtool.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class EntryParam(
    @SerialName("TaskId")
    var taskId: String,

    @SerialName("ProjectPath")
    var projectPath: String,

    @SerialName("TestSelectors")
    var testSelectors: List<String>? = null,

    @SerialName("FileReportPath")
    var fileReportPath: String,
)

fun loadEntryParam(content: String): EntryParam {
    val json = Json { ignoreUnknownKeys = true }
    val data: EntryParam = json.decodeFromString<EntryParam>(content)
    return data
}