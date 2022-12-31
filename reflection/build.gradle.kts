plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

group = "me.santio.utils.reflection"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
}

application {
    mainClass.set("me.santio.utils.reflection.ReflectionUtils")
}