package com.tencent.testsolar.testtool.sdk.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.Test

class TestResultTest {

    @Test
    fun testValidJsonFormat() {
        val result = TestResult(
            test = TestCase(name = "a/b/c/d?name=hello_world", attributes = hashMapOf()),
            context = hashMapOf(),
            startTime = LocalDateTime(2024, 2, 15, 16, 50, 10, 0),
            endTime = LocalDateTime(2024, 2, 15, 16, 57, 21, 21342),
            resultType = ResultType.SUCCEED,
        )

        val re = Json.encodeToString(result)
        re shouldContain "2024-02-15T16:50:10.000Z"
        re shouldContain "2024-02-15T16:57:21.000Z"
        re shouldContain "SUCCEED"
    }
}