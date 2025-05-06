val paperApiVersion = "1.20.4-R0.1-SNAPSHOT" // Check for the latest Paper API version
val jooqVersion = "3.20.4" // Check for the latest jOOQ version
val shadowVersion = "8.3.0" // Check for the latest Shadow plugin version
val mariaDbDriverVersion = "3.3.3"
val jooqSchemaName = "atlasdb_poetrymyno"
val flywayVer = "9.20.0"
project.ext {
    set("jooqVersion", jooqVersion)
    set("paperApiVersion", paperApiVersion)
    set("shadowVersion", shadowVersion)
    set("mariaDbDriverVersion", mariaDbDriverVersion)
    set("jooqSchemaName", jooqSchemaName)
    set("flywayVer", flywayVer)
}

plugins {
    `java-library`
    id("com.gradleup.shadow") version "8.3.0" apply false
    id("nu.studer.jooq") version "10.1" apply false
    id("org.flywaydb.flyway") version "9.20.0" apply false
}



allprojects {
    group = "me.alenalex.rekode"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }
}

subprojects {
    apply(plugin = "java-library")

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    dependencies {
        // Common test dependencies
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        compileOnly("org.jetbrains:annotations:24.0.0")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}