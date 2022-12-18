plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"

    // Apply the application plugin to add support for building a CLI application in Java.
    application

    `maven-publish`
}

group = "me.santio.utils.minecraft"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":common")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.santio.utils"
            artifactId = "minecraft"
            version = version
        }
    }
}