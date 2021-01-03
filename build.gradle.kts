import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.openapi.generator") version "5.0.0"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
}


group = "net.de1mos.ledger"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springCloudVersion"] = "2020.0.0"
extra["testcontainersVersion"] = "1.15.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.r2dbc:r2dbc-postgresql:0.8.6.RELEASE")

    api("io.springfox:springfox-swagger2:3.0.0")
    api("io.springfox:springfox-swagger-ui:3.0.0")
    api("io.swagger:swagger-annotations:1.6.2")
    api("javax.validation:validation-api")
    api("org.openapitools:jackson-databind-nullable:0.1.0")
    api("com.fasterxml.jackson.core:jackson-annotations:2.12.0")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:r2dbc")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val generatedSourcesDir = "$buildDir/generated/openapi"

openApiGenerate {
    generatorName.set("spring")
    outputDir.set(generatedSourcesDir)
    inputSpec.set("$rootDir/src/main/specs/ledger-recorder.yaml")
    configOptions.put("apiPackage", "net.de1mos.ledger.recorder.api")
    configOptions.put("modelPackage", "net.de1mos.ledger.recorder.api.models")
    configOptions.put("bigDecimalAsString", "true")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("reactive", "true")
    configOptions.put("interfaceOnly", "true")
    configOptions.put("skipDefaultInterface", "true")
}

sourceSets {
    getByName("main") {
        java {
            srcDir("$generatedSourcesDir/src/main/java")
        }
    }
}

tasks {
    val openApiGenerate by getting

    val compileJava by getting {
        dependsOn(openApiGenerate)
    }

    val compileKotlin by getting {
        dependsOn(openApiGenerate)
    }
}