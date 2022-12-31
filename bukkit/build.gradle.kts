import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

group = "me.santio.utils.bukkit"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")

    implementation(project(":common"))
    implementation(project(":minecraft"))
    implementation(project(":reflection"))
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
    mainClass.set("me.santio.utils.bukkit.BukkitUtils")
}