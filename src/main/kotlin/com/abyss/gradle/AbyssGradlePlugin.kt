package com.abyss.gradle

import com.abyss.gradle.config.AbyssPluginConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Jar
import java.io.File

class AbyssGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val config = target.extensions.create("kraken", AbyssPluginConfiguration::class.java, target)
        with(target.repositories) {
            mavenCentral()
            maven {
                setUrl("https://jitpack.io")
            }
        }
        with(target.dependencies) {
            add(
                config.configuration.get(),
                "com.github.RsAbyss:AbyssAPI:master-SNAPSHOT"
            )
        }
        target.tasks.register("copy", Copy::class.java) {
            val file = File(config.abyssPluginLocation.get())
            if (file.exists()) {
                from(target.tasks.withType(Jar::class.java))
                into(file.absolutePath)
            }
        }
        target.tasks.register("createPluginConfiguration") {
            val file = target.projectDir.resolve("src/main/resources")
            if (!file.exists()) {
                file.mkdirs()
            }
            val pluginConfig = file.resolve("plugin.ini")
            if (!pluginConfig.exists()) {
                pluginConfig.createNewFile()
            }
            pluginConfig.writeText(config.pluginClass.get())
        }
        target.afterEvaluate {
            target.tasks.withType(Jar::class.java) {
                dependsOn(target.tasks.named("createPluginConfiguration"))
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                target.configurations.named("runtimeClasspath").get()
                    .map { t -> if (t.isDirectory) t else target.zipTree(t) }
                finalizedBy(target.tasks.named("copy"))
            }
        }
    }
}