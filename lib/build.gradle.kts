import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.serialization") version "2.0.10"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
    jacoco
}

fun loadEnv() {
    val envFile = File(".env")
    if (envFile.exists()) {
        val properties = Properties()
        envFile.inputStream().use { properties.load(it) }
        properties.forEach { key, value ->
            println("Load property: $key -> $value")
            System.setProperty(key.toString(), value.toString())
        }
    }
}

loadEnv()

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.6.0")
    implementation("commons-codec:commons-codec:1.17.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-io-core:0.5.1")
    testImplementation("org.amshove.kluent:kluent:1.73")
}

testing {
    suites {
        // Configure the built-in test suite
        @Suppress("UnstableApiUsage")
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test framework
            @Suppress("UnstableApiUsage")
            useKotlinTest("2.0.10")
        }
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11 // 设置 Java 源代码的兼容性版本为 11
    targetCompatibility = JavaVersion.VERSION_11 // 设置 Java 字节码的目标版本为 11
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
    const val VERSION = "0.3.1"
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
                System.getProperty("MAVEN_PUBLISH_URL")
                    ?: System.getenv("MAVEN_PUBLISH_URL")
                    ?: "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            )
            credentials {
                username = System.getProperty("MAVEN_PUBLISH_USERNAME")
                    ?: System.getenv("MAVEN_PUBLISH_USERNAME") ?: "username must be set"
                password = System.getProperty("MAVEN_PUBLISH_PASSWORD")
                    ?: System.getenv("MAVEN_PUBLISH_PASSWORD") ?: "password must be set"
            }
        }
    }
}