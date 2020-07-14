import org.gradle.jvm.tasks.Jar
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
  java
  `java-gradle-plugin`
  `maven-publish`
  application
  id("org.jetbrains.kotlin.jvm") version "1.3.61"
  id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
  kotlin("kapt") version "1.3.61"
}

group = "no.item.kafka.consumer"
version = "1.0.0-SNAPSHOT"
val arrowVersion = "0.10.4"

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation("org.apache.kafka:kafka-clients:2.0.0")
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("io.arrow-kt:arrow-core:$arrowVersion")
  implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
  kapt("io.arrow-kt:arrow-meta:$arrowVersion")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.8.11.1")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.8.9")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.1")
}

ktlint {
  version.set("0.36.0")
  debug.set(false)
  verbose.set(true)
  android.set(false)
  outputToConsole.set(true)
  outputColorName.set("RED")
  ignoreFailures.set(true)
  reporters {
    reporter(ReporterType.PLAIN)
    reporter(ReporterType.CHECKSTYLE)
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

val fatJar = task("fatJar", type = Jar::class) {
  baseName = "${project.name}-fat"
  manifest {
    attributes["Implementation-Title"] = "Gradle Jar File Example"
    attributes["Implementation-Version"] = version
    attributes["Main-Class"] = "no.item.kafka.consumer.MockKafkaConsumerKt"
  }
  exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
  from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
  with(tasks.jar.get() as CopySpec)
}

tasks {
  build {
    dependsOn(fatJar)
  }
}

tasks {
  startScripts {
    dependsOn(fatJar)
    mainClassName = "no.item.kafka.consumer.MockKafkaConsumer"
  }
}
