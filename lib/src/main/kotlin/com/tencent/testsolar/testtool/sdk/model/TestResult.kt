package com.tencent.testsolar.testtool.sdk.model

import com.tencent.testsolar.testtool.sdk.serializer.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class ResultType {
    UNKNOWN,
    SUCCEED,
    FAILED,
    LOAD_FAILED,
    IGNORED,
    RUNNING,
    WAITING,
    FILTERED
}

enum class TestLogLevel {
    TRACE,
    DEBUG,
    INFO,
    WARNNING,
    ERROR
}


@Serializable
data class TestCaseAssertError(
    @SerialName("Expect")
    val expect: String,

    @SerialName("Actual")
    val actual: String,

    @SerialName("Message")
    val message: String
) {
    init {
        require(expect.isNotBlank()) { "TestCaseAssertError.expect must not be black" }
        require(actual.isNotBlank()) { "TestCaseAssertError.actual must not be black" }
        require(message.isNotBlank()) { "TestCaseAssertError.message must not be black" }
    }
}

@Serializable
data class TestCaseRuntimeError(
    @SerialName("Summary")
    val summary: String,

    @SerialName("Detail")
    val detail: String
) {
    init {
        require(summary.isNotBlank()) { "TestCaseRuntimeError.summary must not be black" }
        require(detail.isNotBlank()) { "TestCaseRuntimeError.detail must not be black" }
    }
}

@Serializable
data class TestLog(

    @SerialName("Time")
    @Serializable(with = LocalDateTimeSerializer::class)
    var time: LocalDateTime,

    @SerialName("Level")
    var level: TestLogLevel,

    @SerialName("Content")
    var content: String,

    @SerialName("AssertError")
    var assertError: TestCaseAssertError? = null,

    @SerialName("RuntimeError")
    var runtimeError: TestCaseRuntimeError? = null,
)

@Serializable
data class TestStep(
    @SerialName("StartTime")
    @Serializable(with = LocalDateTimeSerializer::class)
    var startTime: LocalDateTime,

    @SerialName("EndTime")
    @Serializable(with = LocalDateTimeSerializer::class)
    var endTime: LocalDateTime,

    @SerialName("Title")
    var title: String,

    @SerialName("Logs")
    var logs: List<TestLog>,

    @SerialName("ResultType")
    var resultType: ResultType

)

@Serializable
data class TestResult(
    @SerialName("Test")
    var test: TestCase,

    @SerialName("Context")
    var context: HashMap<String, String>,

    @SerialName("StartTime")
    @Serializable(with = LocalDateTimeSerializer::class)
    var startTime: LocalDateTime,

    @SerialName("EndTime")
    @Serializable(with = LocalDateTimeSerializer::class)
    var endTime: LocalDateTime?,

    @SerialName("ResultType")
    var resultType: ResultType,

    @SerialName("Steps")
    var steps: MutableList<TestStep> = mutableListOf(),

    @SerialName("Message")
    var message: String = ""
)
