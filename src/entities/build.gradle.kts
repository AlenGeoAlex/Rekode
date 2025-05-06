import org.jooq.meta.jaxb.ForcedType

plugins {
    id("nu.studer.jooq")
    id("org.flywaydb.flyway")
}

val jooqVersion: String by project.rootProject.extra
val mariaDbDriverVersion: String by project.rootProject.extra
var flywayVer: String by project.rootProject.extra

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        val mariaDbDriverVersion: String by project.rootProject.extra
        var flywayVer: String by project.rootProject.extra

        classpath("org.mariadb.jdbc:mariadb-java-client:$mariaDbDriverVersion")
        classpath("org.flywaydb:flyway-mysql:$flywayVer")
    }
}

dependencies {
    implementation(project(":src:base"))
    implementation("org.jooq:jooq:$jooqVersion")
    jooqGenerator("org.slf4j:slf4j-simple:2.0.9")
    jooqGenerator("org.mariadb.jdbc:mariadb-java-client:$mariaDbDriverVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

flyway {
    val dbUrlEnv = project.findProperty("dbUrl") as String? ?: throw GradleException("dbUrl not set");
    val dbUserEnv = project.findProperty("dbUser") as String? ?: throw GradleException("dbUrl not set");
    val dbPasswordEnv = project.findProperty("dbPassword") as String? ?: throw GradleException("dbUrl not set");

    driver = "org.mariadb.jdbc.Driver"
    url = dbUrlEnv
    password = dbPasswordEnv
    user = dbUserEnv
    validateMigrationNaming = true
    cleanOnValidationError = true
    table = "rekode_schema_history"
}


jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.TRACE
                jdbc.apply {

                    val dbUrlEnv = project.findProperty("dbUrl") as String? ?: throw GradleException("dbUrl not set");
                    val dbUserEnv = project.findProperty("dbUser") as String? ?: throw GradleException("dbUrl not set");
                    val dbPasswordEnv = project.findProperty("dbPassword") as String? ?: throw GradleException("dbUrl not set");

                    driver = "org.mariadb.jdbc.Driver"
                    url = dbUrlEnv
                    user = dbUserEnv
                    password = dbPasswordEnv
                }

                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.mariadb.MariaDBDatabase"
                        schemata.apply {
                            inputSchema = "rekode"
                            outputSchema = "rekode"
                            forcedTypes = listOf<ForcedType>(
                                ForcedType()
                                    .withUserType("java.util.UUID")
                                    .withConverter("me.alenalex.rekode.entities.converters.UUIDConverter")
                                    .withGenericConverter(false)
                                    .withIncludeExpression("(?i).*_id")
                                    .withIncludeTypes("CHAR\\(36\\)"),

                                ForcedType()
                                    .withUserType("me.alenalex.rekode.base.structs.Point")
                                    .withGenericConverter(false)
                                    .withConverter("me.alenalex.rekode.entities.converters.PointConverter")
                                    .withIncludeExpression("(?i)(MIN_POINT|MAX_POINT)")
                                    .withIncludeTypes("VARCHAR\\(500\\)")
                            )
                        }
                    }

                    target.apply {
                        packageName = "${project.group}.gen.entities"
                        directory = "build/generated-sources/jooq/main"
                    }

                    generate.apply {
                        isRecords = true
                        isFluentSetters = true
                        isDaos = true
                        isPojos = true
                        withComments(true)
                        withJavadoc(true)
                    }
                }
            }
        }

    }
}

sourceSets.main.get().java.srcDir("build/generated-sources/jooq/main")

tasks.named("compileJava") {
    dependsOn(tasks.named("generateJooq")) // 'generateJooq' + ConfigurationName
}

