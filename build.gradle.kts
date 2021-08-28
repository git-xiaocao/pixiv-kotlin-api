import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project


plugins {

    application
    kotlin("jvm") version "1.5.30"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.30"
    id("application")
}

group = "top.xiaocao"
version = "0.0.1"


repositories {
    mavenCentral()
}



dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    //这个引擎可以配置代理
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")


}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "16"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "16"
}
