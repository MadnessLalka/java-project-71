import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("java")
    id("io.github.ben-manes.versions") version "0.64.0"
    application
    checkstyle
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.puppycrawl.tools:checkstyle:14.1.0")
}

tasks.test {
    useJUnitPlatform()
}


tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
    revision = "release"
    outputFormatter = "json"
}