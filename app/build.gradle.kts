import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.kotlin.dsl.annotationProcessor

plugins {
    id("java")
    id("io.github.ben-manes.versions") version "0.64.0"
    jacoco
    application
    checkstyle
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

checkstyle {
    toolVersion = "14.1.0"

    application {
        mainClass = "hexlet.code.App"
    }

    jacoco {
        toolVersion = "0.8.15"
    }


    group = "hexlet.code"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.48")
        annotationProcessor("org.projectlombok:lombok:1.18.48")
        testCompileOnly("org.projectlombok:lombok:1.18.48")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.48")
        testImplementation(platform("org.junit:junit-bom:6.1.3"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        implementation("com.puppycrawl.tools:checkstyle:14.1.0")
        implementation("info.picocli:picocli:4.7.7")
        annotationProcessor("info.picocli:picocli-codegen:4.7.7")
    }

    tasks.test {
        useJUnitPlatform()
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
    }

    tasks.test { finalizedBy(tasks.jacocoTestReport) }

    val coverageExcludes = listOf("io/hexlet/Application.class")

    fun JacocoReportBase.excludeEntryPoint() {
        classDirectories.setFrom(
            files(classDirectories.files.map { fileTree(it) { exclude(coverageExcludes) } }),
        )
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
        excludeEntryPoint()
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }

// Порог покрытия: ниже него `./gradlew build` падает,
// и сборка в CI краснеет вместе с ним.
    tasks.jacocoTestCoverageVerification {
        dependsOn(tasks.jacocoTestReport)
        excludeEntryPoint()
        violationRules {
            rule {
                limit {
                    counter = "INSTRUCTION"
                    value = "COVEREDRATIO"
                    minimum = "0.80".toBigDecimal()
                }
            }
        }
    }

    tasks.check { dependsOn(tasks.jacocoTestCoverageVerification) }

    tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
        revision = "release"
        outputFormatter = "json"
    }
}