# mod-template

This repository is a simple template for mods created with [MultiMod](https://github.com/eclipseisoffline/multimod/).
It can be used to create mods for both the Fabric and NeoForge modloaders.

The repository is licensed under CC0.

## Usage

Before starting with coding your mod, you should update the following things:

- This README (duh).
- The LICENSE file and the licenses in `fabric.mod.json` and `neoforge.mods.toml` (unless you want your mod licensed under CC0).
- The name of the root project in `settings.gradle.kts`.
- The constants in `gradle.properties`.
- The versions in `libs.versions.toml` (if coding for a different version of Minecraft).
- The code in the project's modules. Specifically:
  - Move your project's code into a different package.
  - Rename the `mod-template.mixins.json` file.
  - Update the mod initialisers and remove the example mixin in the common module.

Also be sure to change the author information in `fabric.mod.json` and `neoforge.mods.toml`, if you're not me.
