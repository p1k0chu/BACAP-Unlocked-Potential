# BACAP-Unlocked-Potential

BACUP is an addon for [BACAP](https://modrinth.com/datapack/blazeandcaves-advancements-pack) with more advancements.

This "datapack" is actually a mod.  
Because of this, advancements that were impossible before now will be possible.  
It comes bundled with a datapack required to run.

You'll find builds in releases

# How to install

1. Install the mod in your minecraft instance, also install its dependencies:
- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)

2. Install [BACAP](https://bit.ly/3u9BXTr) in your world
    > you dont need to install this addon per-world, if you installed the mod it will work


# Building?

To build this mod you need to clone the repo, and run following commands:
```bash
./gradlew runDatagen
./gradlew build
```

this will make a jar file in `build/libs` folder
