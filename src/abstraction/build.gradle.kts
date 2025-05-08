
val paperApiVersion: String by project.rootProject.extra

dependencies {

    api(project(":src:entities"))
    implementation(project(":src:base"))
    compileOnly("io.papermc.paper:paper-api:${paperApiVersion}")
    api("com.zaxxer:HikariCP:5.1.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
