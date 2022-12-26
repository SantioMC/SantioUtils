import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

group = "me.santio.utils.apis"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":common")))
    implementation(project(mapOf("path" to ":reflection")))

    implementation("com.konghq:unirest-java:3.11.09")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName("build") {
    dependsOn("shadowJar")
}

tasks.getByName<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
}

application {
    mainClass.set("me.santio.utils.apis.APIUtils")
}