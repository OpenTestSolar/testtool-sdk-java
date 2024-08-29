package com.tencent.testsolar.testtool.sdk

import com.tencent.testsolar.testtool.sdk.model.LoadResult
import com.tencent.testsolar.testtool.sdk.model.TestCase
import com.tencent.testsolar.testtool.sdk.model.TestResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.codec.digest.DigestUtils
import java.io.File

// 提供上报用例相关结果到uniSDK
class Reporter(taskId: String, val reportPath: String = "/home/testsolar") {

    private val json = Json { prettyPrint = true }

    init {
        require(taskId.isNotBlank()) { "Reporter.taskId must not be empty!" }
        require(reportPath.isNotBlank()) { "Reporter.reportPath must not be empty!" }
    }

    // 上报加载用例结果
    //
    // 此时reportPath是一个路径
    fun reportLoadResult(loadResult: LoadResult) {
        val raw: String = json.encodeToString(loadResult)

        File(reportPath).parentFile.mkdirs()
        File(reportPath).writeText(raw)
    }

    // 上报测试用例执行结果
    //
    // 此时reportPath是一个目录
    fun reportTestResult(testResult: TestResult) {
        val raw: String = json.encodeToString(testResult)

        File(reportPath).mkdirs()
        File(reportPath, generateRunCaseReportName(testResult.test)).writeText(raw)
    }
}

fun generateRunCaseReportName(test: TestCase): String {
    val retryId: String = test.attributes.getOrDefault("retry", "0")
    val fileName = DigestUtils.md5Hex("${test.name}.${retryId}") + ".json"

    return fileName
}