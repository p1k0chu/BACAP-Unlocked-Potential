import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("net.fabricmc.fabric-loom")
    kotlin("jvm")
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
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    implementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    implementation("net.fabricmc:fabric-language-kotlin:${project.property("kotlin_adapter_version")}")

    implementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_api_version")}")

    testImplementation("net.fabricmc:fabric-loader-junit:${project.property("loader_version")}")
}

tasks.processResources {
    val props = mapOf("version" to project.version)
    inputs.properties(props)

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
