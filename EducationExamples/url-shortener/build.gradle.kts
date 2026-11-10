import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jlleitschuh.gradle.ktlint") version libs.versions.gradle.ktlint
	id("io.gitlab.arturbosch.detekt") version libs.versions.gradle.detekt
	kotlin("jvm") version libs.versions.kotlin
}

group = "io.maksymdobrynin"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.kotlin.stdlib)
	implementation(libs.kotlinx.coroutines)
	testImplementation(libs.junit.jupiter)
	testImplementation(libs.assertj.core)
	testImplementation(libs.mockk)
	testImplementation(libs.kotlinx.coroutines.test)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
