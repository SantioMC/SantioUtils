plugins {
    java
    `maven-publish`
}

val publicationVersion = "1.0"
group = "me.santio.utils"
version = "1.0"

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "java")

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "me.santio.utils"
                artifactId = project.name
                version = publicationVersion

                from(components["java"])
            }
        }
    }
}