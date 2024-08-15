package com.tencent.testsolar.testtool.sdk

import java.nio.file.Files
import java.nio.file.Path

fun <R> withTemporaryDirectory(block: (Path) -> R): R {
    val tempDir = Files.createTempDirectory("tempDirPrefix")
    return try {
        block(tempDir)
    } finally {
        tempDir.toFile().deleteRecursively()
    }
}