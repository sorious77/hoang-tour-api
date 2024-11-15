plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "2.0.20"
}

group = "com.hoang"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

object Version {
    const val KOTEST = "5.9.1"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // mysql
    implementation("com.mysql:mysql-connector-j:9.0.0")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // kotest
    runtimeOnly("io.kotest:kotest-assertions-core:${Version.KOTEST}")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:${Version.KOTEST}")

    // mapstruct
    implementation("org.mapstruct:mapstruct:1.6.0")
    kapt("org.mapstruct:mapstruct-processor:1.6.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
