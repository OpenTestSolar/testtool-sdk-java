package com.tencent.testsolar.testtool.sdk

import com.tencent.testsolar.testtool.sdk.model.TestResult
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.readText

fun readTestResult(filepath: Path): TestResult {
    val json = Json {
        ignoreUnknownKeys = true
    }

    return json.decodeFromString(TestResult.serializer(), filepath.readText())
}