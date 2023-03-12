plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

group = "me.santio.utils.nms"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":reflection"))

    compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
}

application {
    mainClass.set("me.santio.utils.nms.NMSUtils")
}