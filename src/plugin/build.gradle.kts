plugins {
    id("com.gradleup.shadow")
}
val paperApiVersion: String by project.rootProject.extra
val jooqVersion: String by project.rootProject.extra // For shading jOOQ runtime
dependencies {
    implementation(project(":src:abstraction"))
    implementation(project(":src:entities"))
    implementation(project(":src:base"))
    compileOnly("io.papermc.paper:paper-api:${paperApiVersion}")
    implementation("org.jooq:jooq:${jooqVersion}")
    implementation("de.exlll:configlib-yaml:4.6.0")
    implementation("ch.qos.logback:logback-core:1.5.13")
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.2")
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        relocate("org.jooq", "${project.group}.shaded.jooq")
        relocate("org.mariadb.jdbc", "${project.group}.shaded.mariadb.jdbc")
        relocate("com.zaxxer.hikari", "${project.group}.shaded.hikari")
        relocate("org.slf4j", "${project.group}.shaded.slf4j")
        relocate("ch.qos.logback", "${project.group}.shaded.logback")
        relocate("com.google.code.gson", "${project.group}.shaded.gson")
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
