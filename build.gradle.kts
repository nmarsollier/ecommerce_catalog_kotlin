import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    id("java")
    application
    kotlin("jvm") version "1.8.0"
}

group = "com.nmarsollier.catalog.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_19.toString()
    targetCompatibility = JavaVersion.VERSION_19.toString()
    options.release.set(19)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "19"
    }
}

application {
    mainClass = "ServerKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8", "1.8.0"))
    implementation("io.insert-koin:koin-core:3.4.3")
    implementation("io.insert-koin:koin-logger-slf4j:3.4.3")

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    implementation("io.ktor:ktor-serialization-gson-jvm:2.3.2")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.2")
    implementation("io.ktor:ktor-server-core-jvm:2.3.2")
    implementation("io.ktor:ktor-server-cors:2.3.2")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.12")
    implementation("io.ktor:ktor-server-call-logging:2.3.2")
    implementation("io.ktor:ktor-server-status-pages:2.3.2")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.guava:guava:33.3.0-jre")
    implementation("com.rabbitmq:amqp-client:5.21.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")

    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.slf4j:slf4j-simple:2.0.7")
}

tasks.test {
    useJUnitPlatform()
}
