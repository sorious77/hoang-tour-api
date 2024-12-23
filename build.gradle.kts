plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "2.0.20"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
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
    const val JDSL = "3.5.3"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
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

    // spring security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")

    // JDSL
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${Version.JDSL}")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:${Version.JDSL}")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${Version.JDSL}")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
