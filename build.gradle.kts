import me.modmuss50.mpp.ReleaseType

plugins {
    id("xyz.eclipseisoffline.multimod.modding-fabric-conventions")
}

group = properties["maven_group"] as String

mod {
    id = properties["mod_id"] as String
    name = properties["mod_name"] as String
    description = properties["mod_description"] as String

    archivesBaseName = properties["archives_base_name"] as String

    minecraft = libs.minecraft
    // TODO parchment, maven repositories
    fabricLoader = libs.fabric.loader
    fabricApi = libs.fabric.api

    supportedMinecraftVersions = libs.versions.minecraft.supported

    modrinthId = properties["modrinth_project_id"] as String
    releaseType = ReleaseType.of(properties["release_type"] as String)
    releaseVersions = libs.versions.minecraft.release
    githubRepository = properties["github_repository"] as String
    gitBranch = properties["git_branch"] as String

    finishConfiguring()
}
