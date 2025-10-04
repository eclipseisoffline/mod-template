import me.modmuss50.mpp.ReleaseType

plugins {
    alias(libs.plugins.multimod)
}

group = properties["maven_group"] as String
version = properties["version"] as String

mod {
    id = properties["mod_id"] as String
    name = properties["mod_name"] as String
    description = properties["mod_description"] as String

    archivesBaseName = properties["archives_base_name"] as String

    minecraft = libs.minecraft
    neoFormTimestamp = "20250930.151910" // TODO
    // TODO parchment, maven repositories

    fabricApi = libs.fabric.api

    neoForgeVersion = libs.versions.neoforge

    supportedMinecraftVersions = libs.versions.minecraft.supported

    modrinthId = properties["modrinth_project_id"] as String
    releaseType = ReleaseType.of(properties["release_type"] as String)
    releaseVersions = libs.versions.minecraft.release
    githubRepository = properties["github_repository"] as String
    gitBranch = properties["git_branch"] as String
}
