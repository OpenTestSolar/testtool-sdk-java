package com.tencent.testsolar.testtool.sdk.model

import org.amshove.kluent.shouldBeEqualTo
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
}