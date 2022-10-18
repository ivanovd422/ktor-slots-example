val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project


plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.1"
                id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-metrics-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // db
    implementation ("org.jetbrains.exposed:exposed-core:0.35.1")
    implementation ("org.jetbrains.exposed:exposed-dao:0.35.1")
    implementation ("org.jetbrains.exposed:exposed-jdbc:0.35.1")
    implementation ("org.postgresql:postgresql:42.2.2")
    implementation ("com.zaxxer:HikariCP:3.4.2")

    // crypt
    implementation ("org.mindrot:jbcrypt:0.4")


    // Koin for Ktor
    implementation ("io.insert-koin:koin-ktor:$koin_version")
// SLF4J Logger
    implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")

    // Testing
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}