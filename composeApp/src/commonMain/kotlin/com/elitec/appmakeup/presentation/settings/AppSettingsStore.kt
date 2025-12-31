package com.elitec.appmakeup.presentation.settings

import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path

class AppSettingsStore(
    private val fileSystem: FileSystem,
    private val json: Json
) {

    private val settingsFile = appSettingsPath() / "settings.json"

    fun load(): AppSettings {
        if (!fileSystem.exists(settingsFile)) return AppSettings()

        return fileSystem.read(settingsFile) {
            json.decodeFromString(readUtf8())
        }
    }

    fun save(settings: AppSettings) {
        fileSystem.createDirectories(appSettingsPath())
        fileSystem.write(settingsFile) {
            writeUtf8(json.encodeToString(settings))
        }
    }
}