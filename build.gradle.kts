import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.7.0"
    val miraiVersion = "2.12.1"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("net.mamoe.mirai-console") version miraiVersion
}

group = "org.vworks.mirai"
version = "1.1.1"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation(kotlin("stdlib-jdk8"))
}

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}