plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

val selenium_version = "4.21.0"
val jackson_version = "2.17.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(20)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.core:jackson-databind")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.mockito")
	}
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

	testImplementation("org.seleniumhq.selenium:selenium-java:$selenium_version")
	testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:$selenium_version")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
