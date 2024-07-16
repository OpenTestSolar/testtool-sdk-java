package com.tencent.testsolar.testtool.sdk

import com.tencent.testsolar.testtool.sdk.model.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
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
                logs = listOf(
                    TestLog(
                        time = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                        level = TestLogLevel.INFO,
                        content = "content",
                        assertError = TestCaseAssertError(
                            expect = "aa",
                            actual = "content",
                            message = "not equal"
                        ),
                        runtimeError = TestCaseRuntimeError(
                            summary = "aaa",
                            detail = "content",
                        )
                    )
                )
            )
        )
    )

    @Test
    fun reportLoadResult() {
        val reporter = Reporter("aabbcc", "/tmp/testsolar")
        val loadResult = LoadResult(
            tests = mutableListOf(
                TestCase(name = "aa/bb/cc?dd", attributes = hashMapOf()),
                TestCase(name = "aa/bb/cc?ee", attributes = hashMapOf()),
            ),
            loadErrors = mutableListOf(
                LoadError(name = "load error a", message = "load error"),
                LoadError(name = "load error b", message = "load error"),
            )
        )
        reporter.reportLoadResult(loadResult)
        File(reporter.reportDir, "result.json").exists() shouldBe true

        val json = Json { ignoreUnknownKeys = true }
        val trJson = File(reporter.reportDir, "result.json").readText()
        val tr = json.decodeFromString<LoadResult>(trJson)
        tr shouldBeEqualTo loadResult
    }

    @Test
    fun reportTestResult() {
        val reporter = Reporter("xyz", "/tmp/testsolar")

        reporter.reportTestResult(testResult)

        File(reporter.reportDir, "bdbb21659ad26cf715254da0044ea375.json").exists() shouldBe true

        val json = Json { ignoreUnknownKeys = true }
        val trJson = File(reporter.reportDir, "bdbb21659ad26cf715254da0044ea375.json").readText()
        val tr = json.decodeFromString<TestResult>(trJson)
        tr.test.name shouldBeEqualTo testResult.test.name
        tr.test.attributes shouldBeEqualTo testResult.test.attributes
        tr.context shouldBeEqualTo testResult.context
        tr.startTime.second shouldBeEqualTo testResult.startTime.second
        tr.endTime?.second shouldBeEqualTo testResult.endTime?.second
        tr.resultType shouldBeEqualTo testResult.resultType
        tr.steps.size shouldBeEqualTo testResult.steps.size
        val step = tr.steps.first()

        step.startTime.second shouldBeEqualTo testResult.startTime.second
        step.endTime.second shouldBeEqualTo testResult.endTime?.second
        step.resultType shouldBeEqualTo testResult.resultType
        step.title shouldBeEqualTo "hello"
        step.logs.size shouldBeEqualTo 1
        step.logs[0].time.second shouldBeEqualTo testResult.startTime.second
        step.logs[0].level shouldBeEqualTo TestLogLevel.INFO
        step.logs[0].content shouldBeEqualTo "content"
        step.logs[0].assertError?.expect shouldBeEqualTo "aa"
        step.logs[0].assertError?.actual shouldBeEqualTo "content"
        step.logs[0].assertError?.message shouldBeEqualTo "not equal"
        step.logs[0].runtimeError?.summary shouldBeEqualTo "aaa"
        step.logs[0].runtimeError?.detail shouldBeEqualTo "content"
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
            File(reporter.reportDir).deleteRecursively()
        }
    }
}