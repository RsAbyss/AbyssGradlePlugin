import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.0.0-rc-1"
    `kotlin-dsl`
}

group = "com.abyss.gradle"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("gradle-plugin"))
    testImplementation(kotlin("test"))
}

pluginBundle {
    website = "http://Abyss.ninija"
    vcsUrl = "https://github.com/RsAbyss/AbyssAPI"
}

gradlePlugin {
    plugins {
        create("abyssPlugin") {
            id = "com.abyss.plugin"
            displayName = "Abyss Gradle Plugin"
            description = "Provides tasks to make development of Abyss plugins easier."
            implementationClass = "com.abyss.gradle.AbyssGradlePlugin"
            version = "1.0-SNAPSHOT"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    repositories {
        mavenLocal()
    }
}