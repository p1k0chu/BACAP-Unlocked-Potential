import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("net.fabricmc.fabric-loom")
    kotlin("jvm")
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
    accessWidenerPath = file("src/main/resources/bacapup.classtweaker")

    mods {
        register(project.name) {
            sourceSet("main")
            sourceSet("client")
        }
    }
}

fabricApi {
    configureDataGeneration()

    configureTests {
		createSourceSet = true
		modId = "test-bacapup"
        // Agree to Minecraft EULA
		eula = true
	}
}

dependencies {
    minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")
    implementation("net.fabricmc:fabric-language-kotlin:${providers.gradleProperty("kotlin_adapter_version").get()}")

    implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")

    testImplementation("net.fabricmc:fabric-loader-junit:${providers.gradleProperty("loader_version").get()}")
}

tasks.processResources {
    val props = mapOf(
        "version" to project.version
    )
    props.forEach { k, v -> inputs.property(k ,v) }

    filesMatching("fabric.mod.json") {
        expand(props)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 25
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
    }

}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    val name = project.name
    inputs.property("name", name)
    from("LICENSE") {
        rename { "${it}_$name" }
    }
}

tasks.test {
	useJUnitPlatform()
}
