plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
    `maven-publish` // Add ./gradlew publishToMavenLocal
    checkstyle // Ensures correctly formatted code
    pmd // Code quality checks
    id("org.sonarqube") version "6.0.1.5171" // Advanced code quality checks
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("io.papermc.hangar-publish-plugin") version "0.1.3"
}

group="fr.formiko.opitemsremover"
version="1.1.1"
description="Disable some items."
java.sourceCompatibility = JavaVersion.VERSION_21
var mainMinecraftVersion = "1.21.5"
val supportedMinecraftVersions = "1.20 - 1.21.5"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:$mainMinecraftVersion-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
}

checkstyle {
    toolVersion = "10.12.4"
    configFile = rootProject.file("config/checkstyle/checkstyle.xml")
}

pmd {
    isConsoleOutput = true
    toolVersion = "7.0.0"
    rulesMinimumPriority = 5
    ruleSets = listOf("category/java/errorprone.xml", "category/java/bestpractices.xml")
}

sonar {
  properties {
    property("sonar.projectKey", "Mvndi_" + project.name)
    property("sonar.projectName", project.name)
    property("sonar.host.url", "https://mvndisonar.formiko.fr")
  }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    shadowJar {
        val prefix = "${project.group}.lib"
        sequenceOf(
            "co.aikar",
            "org.bstats",
        ).forEach { pkg ->
            relocate(pkg, "$prefix.$pkg")
        }

        archiveFileName.set("${project.name}-${project.version}.jar")
    }
    assemble {
        dependsOn(shadowJar)
    }
    processResources {
        val props = mapOf(
            "name" to project.name,
            "version" to project.version,
            "description" to project.description,
            "apiVersion" to "1.20",
            "group" to project.group
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
    runServer {
        minecraftVersion("$mainMinecraftVersion")
    }
}


@Suppress("UnstableApiUsage")
tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}

tasks.register("echoVersion") {
    doLast {
        println("${project.version}")
    }
}

tasks.register("echoReleaseName") {
    doLast {
        println("${project.version} [${supportedMinecraftVersions}]")
    }
}

val versionString: String = version as String
val isRelease: Boolean = !versionString.contains("SNAPSHOT")

hangarPublish { // ./gradlew publishPluginPublicationToHangar
    publications.register("plugin") {
        version.set(project.version as String)
        channel.set(if (isRelease) "Release" else "Snapshot")
        id.set(project.name)
        apiKey.set(System.getenv("HANGAR_API_TOKEN"))
        platforms {
            register(io.papermc.hangarpublishplugin.model.Platforms.PAPER) {
                url = "https://github.com/HydrolienF/"+project.name+"/releases/download/"+versionString+"/"+project.name+"-"+versionString+".jar"

                // Set platform versions from gradle.properties file
                val versions: List<String> = supportedMinecraftVersions.replace(" ", "").split(",")
                platformVersions.set(versions)
            }
        }
    }
}