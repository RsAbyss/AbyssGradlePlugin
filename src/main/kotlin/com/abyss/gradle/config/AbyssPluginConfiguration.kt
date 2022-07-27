package com.abyss.gradle.config

import org.gradle.api.Project
import org.gradle.api.provider.Property

abstract class AbyssPluginConfiguration(val project: Project) {
    abstract val pluginClass: Property<String>
    abstract val abyssPluginLocation: Property<String>
    abstract val configuration: Property<String>

    init {
        pluginClass.convention("")
        abyssPluginLocation.convention(System.getProperty("user.home"))
        configuration.convention("implementation")
    }
}