plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.plugin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "io.maksymdobrynin.rabbitmqdemo"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.boot.starter.webflux)
	implementation(libs.spring.boot.starter.logging)
	implementation(libs.spring.boot.starter.validation)
	implementation(libs.spring.boot.kafka)
	implementation(libs.reactor.kotlin.extensions)
	implementation(libs.kotlin.reflect)
	implementation(libs.kotlinx.coroutines.reactor)

	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.reactor.test)
	testImplementation(libs.kotlin.test.junit5)
	testImplementation(libs.kotlinx.coroutines.test)
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
