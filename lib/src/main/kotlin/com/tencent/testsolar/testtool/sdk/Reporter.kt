package com.tencent.testsolar.testtool.sdk

import com.tencent.testsolar.testtool.sdk.model.LoadResult
import com.tencent.testsolar.testtool.sdk.model.TestResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.codec.digest.DigestUtils
import java.io.File

const val LOAD_RESULT_FILE_NAME = "result.json"

// 提供上报用例相关结果到uniSDK
class Reporter(taskId: String, val reportDir: String = "/home/testsolar") {



    private val json = Json { prettyPrint = true }

    init {
        require(taskId.isNotBlank()) { "Reporter.taskId must not be empty!" }
        require(reportDir.isNotBlank()) { "Reporter.reportDir must not be empty!" }
    }

    // 上报加载用例结果
    fun reportLoadResult(loadResult: LoadResult) {
        val raw: String = json.encodeToString(loadResult)

        File(reportDir).mkdirs()
        File(reportDir, LOAD_RESULT_FILE_NAME).writeText(raw)
    }

    // 上报测试用例执行结果
    fun reportTestResult(testResult: TestResult) {
        val raw: String = json.encodeToString(testResult)

        File(reportDir).mkdirs()
        File(reportDir, generateRunCaseReportName(testResult)).writeText(raw)
    }

    fun generateRunCaseReportName(testResult: TestResult): String {
        val retryId: String = testResult.test.attributes.getOrDefault("retry", "0")
        val fileName = DigestUtils.md5Hex("${testResult.test.name}.${retryId}") + ".json"

        return fileName
    }
}