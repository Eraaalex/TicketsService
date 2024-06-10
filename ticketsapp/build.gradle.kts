plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.hse.software.construction.ticketsapp"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

val springdocOpenApi: String by project
val jjwtVersion: String by project

dependencies {
	implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
	implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
	implementation("io.jsonwebtoken:jjwt:$jjwtVersion")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenApi")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
	implementation("org.liquibase:liquibase-core")

	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("javax.servlet:javax.servlet-api:4.0.1")

	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.2.RELEASE")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-freemarker:3.2.2")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.jetbrains:annotations:24.0.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	compileOnly("javax.servlet:javax.servlet-api:4.0.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
