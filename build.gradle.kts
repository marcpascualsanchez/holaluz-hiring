import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import Build_gradle.Versions.cucumber
import Build_gradle.Versions.junit
import Build_gradle.Versions.wiremock
import Build_gradle.Versions.mockk

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.7.10"
}

group = "com.marcpascualsanchez"
version = "0.0.1-SNAPSHOT"

object Versions {
	const val cucumber = "7.8.1"
	const val wiremock = "2.27.2"
	const val junit = "5.4.0"
	const val mockk = "1.12.5"
}

repositories {
	mavenCentral()
	maven("https://packages.confluent.io/maven/")
}

sourceSets {
	create("e2eTest") {
		compileClasspath += sourceSets.main.get().output
		runtimeClasspath += sourceSets.main.get().output
	}
}

val e2eTestImplementation: Configuration by configurations.getting {
	extendsFrom(configurations.implementation.get())
}

configurations["e2eTestImplementation"].extendsFrom(configurations.runtimeOnly.get())

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	testImplementation("io.mockk:mockk:$mockk")
	testImplementation("org.junit.jupiter:junit-jupiter:$junit")
	testImplementation("io.cucumber:cucumber-junit:$cucumber")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	e2eTestImplementation("org.springframework.boot:spring-boot-starter-test")
	e2eTestImplementation("com.github.tomakehurst:wiremock:$wiremock")
	e2eTestImplementation("io.cucumber:cucumber-java:$cucumber")
	e2eTestImplementation("io.cucumber:cucumber-junit:$cucumber")
	e2eTestImplementation("io.cucumber:cucumber-spring:$cucumber")
	e2eTestImplementation("org.junit.jupiter:junit-jupiter:$junit")
}

val e2eTest = task<Test>("e2eTest") {
	description = "Runs e2e tests."
	group = "verification"

	testClassesDirs = sourceSets["e2eTest"].output.classesDirs
	classpath = sourceSets["e2eTest"].runtimeClasspath
}

java {
	targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.named<JavaExec>("bootRun") {
	if (project.hasProperty("inputFiles")) {
		args = mutableListOf(project.property("inputFiles") as String)
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "1.8"
	}
}

tasks.test {
	useJUnitPlatform()
}

tasks.getByName<Test>("e2eTest") {
	useJUnitPlatform()
}