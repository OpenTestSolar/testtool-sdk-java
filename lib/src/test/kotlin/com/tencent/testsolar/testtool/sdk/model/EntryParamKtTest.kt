package com.tencent.testsolar.testtool.sdk.model

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.Test

class EntryParamKtTest {

    @Test
    fun loadEntryParamTest() {
        javaClass.getResource("/entry_param.json")?.run {
            val entry = loadEntryParam(this.readText())

            entry.taskId shouldBeEqualTo "aa"
            entry.projectPath shouldBeEqualTo "/noroot/home/baby"
            entry.testSelectors shouldBeEqualTo listOf("test_normal_case?test_success")
            entry.fileReportPath shouldBeEqualTo "/tmp/var/x/j29u83/xuh89/run"
        }
    }

    @Test
    fun saveEntryParamTest() {
        val json = Json { ignoreUnknownKeys = true }

        javaClass.getResource("/entry_param.json")?.run {
            val entry = loadEntryParam(this.readText())

            val input = EntryParam(
                taskId = entry.taskId,
                projectPath = entry.projectPath,
                testSelectors = entry.testSelectors,
                fileReportPath = entry.fileReportPath,
            )
            input.taskId = entry.taskId
            input.projectPath = entry.projectPath
            input.testSelectors = entry.testSelectors
            input.fileReportPath = entry.fileReportPath

            input shouldBeEqualTo entry

            val jsonContent = json.encodeToString(entry)

            jsonContent shouldContain "/noroot/home/baby"
        }
    }
}