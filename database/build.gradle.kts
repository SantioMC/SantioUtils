plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

group = "me.santio.utils.database"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.litote.kmongo:kmongo:4.8.0")
}

application {
    mainClass.set("me.santio.utils.apis.APIUtils")
}