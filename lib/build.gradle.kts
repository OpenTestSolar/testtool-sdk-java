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
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.7".toBigDecimal()
            }
        }
    }
}

// ------------------------------------
// PUBLISHING TO SONATYPE CONFIGURATION
// ------------------------------------
object Meta {
    const val GROUP = "com.tencent.testsolar"
    const val ARTIFACT_ID = "test_tool_sdk"
    const val VERSION = "0.2.0"
    const val DESC = "Java SDK for TestSolar TestTool"
    const val LICENSE = "Apache-2.0"
    const val LICENSE_URL = "https://opensource.org/licenses/Apache-2.0"
    const val GITHUB_REPO = "OpenTestSolar/testtool-sdk-java.git"
    const val DEVELOPER_ID = "asiazhang"
    const val DEVELOPER_NAME = "asiazhang"
    const val DEVELOPER_EMAIL = "asiazhang@gmail.com"
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            // 设置发布的artifact的groupId, artifactId和version
            groupId = Meta.GROUP
            artifactId = Meta.ARTIFACT_ID
            version = Meta.VERSION

            // 如果需要，可以添加其他元数据
            pom {
                name = Meta.ARTIFACT_ID
                description = Meta.DESC
                url = "https://github.com/${Meta.GITHUB_REPO}"

                licenses {
                    license {
                        name = Meta.LICENSE
                        url = Meta.LICENSE_URL
                    }
                }

                developers {
                    developer {
                        id = Meta.DEVELOPER_ID
                        name = Meta.DEVELOPER_NAME
                        email = Meta.DEVELOPER_EMAIL
                    }
                }

                scm {
                    url = "https://github.com/${Meta.GITHUB_REPO}"
                    connection = "scm:git:https://github.com/${Meta.GITHUB_REPO}"
                    developerConnection = "scm:git:https://github.com/${Meta.GITHUB_REPO}"
                }

                issueManagement {
                    system = "GitHub"
                    url = "https://github.com/${Meta.GITHUB_REPO}/issues"
                }
            }
        }
    }

    repositories {
        maven {
            url = uri(
                System.getenv("MAVEN_PUBLISH_URL") ?: "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            )
            credentials {
                username = System.getenv("MAVEN_PUBLISH_USERNAME") ?: "username must be set"
                password = System.getenv("MAVEN_PUBLISH_PASSWORD") ?: "password must be set"
            }
        }
    }
}