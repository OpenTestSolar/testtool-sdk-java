package com.tencent.testsolar.testtool.sdk.model

import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow
import kotlin.test.Test

class TestCaseAssertErrorTest {

    @Test
    fun validateAssertError() {
        invoking {
            TestCaseAssertError(
                expect = "",
                actual = "aaa",
                message = "message",
            )
        } shouldThrow IllegalArgumentException::class

        invoking {
            TestCaseAssertError(
                expect = "aa",
                actual = "",
                message = "message",
            )
        } shouldThrow IllegalArgumentException::class

        invoking {
            TestCaseAssertError(
                expect = "aa",
                actual = "bb",
                message = "",
            )
        } shouldThrow IllegalArgumentException::class
    }

    @Test
    fun validTestCaseRuntimeError() {
        invoking {
            TestCaseRuntimeError(
                summary = "",
                detail = "message",
            )
        } shouldThrow IllegalArgumentException::class

        invoking {
            TestCaseRuntimeError(
                summary = "aa",
                detail = "",
            )
        } shouldThrow IllegalArgumentException::class
    }
}