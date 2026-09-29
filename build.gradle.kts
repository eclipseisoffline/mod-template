plugins {
    alias(libs.plugins.multimod)
}

group = providers.gradleProperty("maven_group").get()
version = providers.gradleProperty("version").get()

multimod {
    id = providers.gradleProperty("mod_id")
    name = providers.gradleProperty("mod_name")
    description = providers.gradleProperty("mod_description")

    archivesBaseName = providers.gradleProperty("archives_base_name")

    minecraft {
        minecraft = libs.minecraft
        supportedMinecraftVersions = ">=26.3 ~26.3-"
        neoForgeSupportedMinecraftVersions = "26.3"
    }

    resourceConfiguration.defaults()

    fabricApi = libs.fabric.api
    neoForgeVersion = libs.versions.neoforge

    publishing {
        maven {
            name = "eclipseisoffline"
            url = uri("https://maven.eclipseisoffline.xyz/releases")
            credentials(PasswordCredentials::class)
        }
    }
}
