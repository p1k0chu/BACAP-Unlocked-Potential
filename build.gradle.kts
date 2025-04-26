plugins {
    id("fabric-loom") version "1.10-SNAPSHOT"
    kotlin("jvm") version "2.1.20"
}

val mod_version: String by project
val minecraft_version: String by project
val loader_version: String by project
val maven_group: String by project
val yarn_mappings: String by project
val kotlin_adapter_version: String by project
val archives_base_name: String by project

base {
    archivesName = archives_base_name
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}

loom {
    splitEnvironmentSourceSets()

    mods {
        register("bac-unlocked-potential") {
            sourceSet("main")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_mappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loader_version")
    modImplementation("net.fabricmc:fabric-language-kotlin:$kotlin_adapter_version")

    // modImplementation ("net.fabricmc.fabric-api:fabric-api:${project.fabric_version}")

}

tasks.processResources {
	filesMatching("fabric.mod.json") {
		expand (
            "version" to mod_version,
            "minecraft_version" to minecraft_version,
            "loader_version" to loader_version,
            "kotlin_adapter_version" to kotlin_adapter_version
        )
	}
}

tasks.withType(JavaCompile::class).configureEach {
    options.release = 21
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_$archives_base_name" }
    }
}
