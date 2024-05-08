/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.6/userguide/building_java_projects.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
    jacoco
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.5.0")
    implementation("commons-codec:commons-codec:1.17.0")

    testImplementation("org.amshove.kluent:kluent:1.73")
}

testing {
    suites {
        // Configure the built-in test suite
        @Suppress("UnstableApiUsage")
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test framework
            @Suppress("UnstableApiUsage")
            useKotlinTest("1.9.20")
        }
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required = true
    }
}