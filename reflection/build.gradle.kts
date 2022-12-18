plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
    `maven-publish`
}

group = "me.santio.utils.reflection"
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

tasks.getByName("build") {
    dependsOn("shadowJar")
}

application {
    mainClass.set("me.santio.utils.reflection.ReflectionUtils")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.santio.utils"
            artifactId = "reflection"
            version = version
        }
    }
}