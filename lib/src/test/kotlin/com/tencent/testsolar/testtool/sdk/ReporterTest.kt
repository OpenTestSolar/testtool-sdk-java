package com.tencent.testsolar.testtool.sdk

import com.tencent.testsolar.testtool.sdk.model.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import java.io.File

class ReporterTest {

    private val testResult = TestResult(
        test = TestCase(name = "bb/aa/cc/dd?name=compute_fa", attributes = hashMapOf()),
        context = hashMapOf(),
        startTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
        endTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
        resultType = ResultType.SUCCEED,
        steps = mutableListOf(
            TestStep(
                startTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                endTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                title = "hello",
                resultType = ResultType.SUCCEED,
                logs = listOf()
            )
        )
    )

    @Test
    fun reportLoadResult() {
        val reporter = Reporter("aabbcc", "/tmp/testsolar")

        reporter.reportLoadResult(LoadResult())

        File(reporter.loadReportDir, "result.json").exists() shouldBe true
    }

    @Test
    fun reportTestResult() {
        val reporter = Reporter("xyz", "/tmp/testsolar")

        reporter.reportTestResult(testResult)

        File(reporter.runReportDir, "bdbb21659ad26cf715254da0044ea375.json").exists() shouldBe true
    }

    @Test
    fun generateRunCaseReportName() {
        val reporter = Reporter("xyz")
        val path = reporter.generateRunCaseReportName(testResult)

        path shouldBeEqualTo "bdbb21659ad26cf715254da0044ea375.json"
    }

    companion object {
        @JvmStatic
        @AfterAll
        fun cleanup() {
            val reporter = Reporter("1")
            File(reporter.loadReportDir).deleteRecursively()
            File(reporter.runReportDir).deleteRecursively()
        }
    }
}