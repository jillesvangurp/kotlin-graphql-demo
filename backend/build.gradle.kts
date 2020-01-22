import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
	id("project-report")
}

group = "com.jillesvangurp.graphql"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	maven(url="https://jitpack.io")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.expediagroup:graphql-kotlin-spring-server:2.0.0.RC3")
	implementation("com.github.jillesvangurp:es-kotlin-wrapper-client:1.0-M6-7.5.1")

	// for some reason gradle pulls in ancient shit unless you get the right version of all four of these.
	api("org.elasticsearch:elasticsearch:7.5.1")
	api("org.elasticsearch.client:elasticsearch-rest-client:7.5.1")
	api("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.5.1")
	api("org.elasticsearch.client:elasticsearch-rest-client-sniffer:7.5.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
