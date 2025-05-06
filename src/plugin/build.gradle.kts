plugins {
    id("com.gradleup.shadow")
}
val paperApiVersion: String by project.rootProject.extra
val jooqVersion: String by project.rootProject.extra // For shading jOOQ runtime
dependencies {
    implementation(project(":src:abstraction"))
    // Paper API - compileOnly because the server provides it
    compileOnly("io.papermc.paper:paper-api:${paperApiVersion}")
// jOOQ runtime - We need to shade this into our plugin JAR
    implementation("org.jooq:jooq:${jooqVersion}")
}

tasks {
    // Configure the shadowJar task to create a fat JAR
    shadowJar {
        archiveClassifier.set("") // No '-all' or '-shadow' suffix in the JAR name

        relocate("org.jooq", "${project.group}.shaded.jooq")
        relocate("org.mariadb.jdbc", "${project.group}.shaded.mariadb.jdbc") // <-- ADD THIS
    }

    // Make the standard 'build' task depend on 'shadowJar'
    build {
        dependsOn(shadowJar)
    }

    // Process plugin.yml to replace placeholders like version
    processResources {
        filesMatching("plugin.yml") {
            expand(project.properties) // Allows using ${project.version}, ${project.name} etc.
        }
    }
}