import me.modmuss50.mpp.ReleaseType
import net.fabricmc.loom.task.RemapJarTask

plugins {
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.mod.publish.plugin)
    alias(libs.plugins.maven.publish)
}

group = properties["maven_group"] as String

val modId = properties["mod_id"] as String
val modName = properties["mod_name"] as String
val modDescription = properties["mod_description"] as String

val releaseType = ReleaseType.of(properties["release_type"] as String)
val modrinthId = properties["modrinth_project_id"] as String
val githubRepository = properties["github_repository"] as String
val gitBranch = properties["git_branch"] as String

val targetJavaVersion = 21

base {
    archivesName = properties["archives_base_name"] as String
}

repositories {}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)

    modImplementation(libs.fabric.api)
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create(modId) {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["client"])
        }
    }
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.toVersion(targetJavaVersion)
    targetCompatibility = JavaVersion.toVersion(targetJavaVersion)
}

publishing {
    repositories {
        maven {
            name = "eclipseisoffline"
            url = uri("https://maven.eclipseisoffline.xyz/releases")
            credentials(PasswordCredentials::class)
        }
    }

    publications {
        create<MavenPublication>("maven") {
            artifactId = project.base.archivesName.get()
            from(components["java"])
        }
    }
}

publishMods {
    changelog = File("CHANGELOG.md").readText()
    type = releaseType

    file = tasks.getByName<RemapJarTask>("remapJar").archiveFile
    modLoaders.add("fabric")

    modrinth {
        accessToken = providers.gradleProperty("MODRINTH_API_TOKEN")
        projectId = modrinthId
        minecraftVersions.addAll(libs.versions.minecraft.release.get().split(","))
    }

    github {
        accessToken = providers.gradleProperty("GITHUB_API_PUBLISH_TOKEN")
        repository = "eclipseisoffline/$githubRepository"
        commitish = gitBranch
    }
}

tasks {
    processResources {
        inputs.property("mod_id", modId)
        inputs.property("mod_name", modName)
        inputs.property("mod_description", modDescription)
        inputs.property("version", project.version)
        inputs.property("minecraft_version", libs.versions.minecraft.supported.get())
        inputs.property("loader_version", libs.versions.fabric.loader.get())
        inputs.property("fabric_api_version", libs.versions.fabric.api.get())
        inputs.property("modrinth_id", modrinthId)
        inputs.property("github_repository", githubRepository)
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(
                "mod_id" to modId,
                "mod_name" to modName,
                "mod_description" to modDescription,
                "version" to project.version,
                "minecraft_version" to libs.versions.minecraft.supported.get(),
                "loader_version" to libs.versions.fabric.loader.get(),
                "fabric_api_version" to libs.versions.fabric.api.get(),
                "modrinth_id" to modrinthId,
                "github_repository" to githubRepository
            )
        }
    }

    withType<JavaCompile>().configureEach {
        options.release = targetJavaVersion
    }

    jar {
        inputs.property("archivesName", project.base.archivesName)

        from("LICENSE") {
            rename {
                "${it}_${project.base.archivesName}"
            }
        }
    }

    publish {
        dependsOn("publishMods")
    }
}
