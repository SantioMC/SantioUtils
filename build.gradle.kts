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

        repositories {
            maven {
                url = uri("https://repo.repsy.io/mvn/santio/santioutils")
                credentials {
                    username = project.property("repsyUsername") as String
                    password = project.property("repsyPassword") as String
                }
            }
        }
    }
}