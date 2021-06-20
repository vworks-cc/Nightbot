plugins {
    val kotlinVersion = "1.5.10"
    val miraiVersion = "2.7-M1"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version miraiVersion
}

group = "org.vworks.mirai"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")
}

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
