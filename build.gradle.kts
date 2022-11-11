plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.testng:testng:7.6.1")
}

tasks.getByName<Test>("test") {
    useTestNG {
        listeners.add("org.jetbrains.teamcity.testPrioritization.CustomOrder")
    }
}
