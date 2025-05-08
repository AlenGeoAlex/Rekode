
plugins {
    id("io.freefair.lombok") version "8.13.1"
}


dependencies {
    api("org.slf4j:slf4j-api:1.7.36")
    api("com.google.code.gson:gson:2.13.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}