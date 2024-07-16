package com.tencent.testsolar.testtool.sdk.model

import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow
import kotlin.test.Test

class LoadErrorTest {

    @Test
    fun validateLoadError() {
        invoking {
            LoadError(
                name = "",
                message = "aaa"
            )
        } shouldThrow IllegalArgumentException::class

        invoking {
            LoadError(
                name = "aa",
                message = ""
            )
        } shouldThrow IllegalArgumentException::class
    }

}