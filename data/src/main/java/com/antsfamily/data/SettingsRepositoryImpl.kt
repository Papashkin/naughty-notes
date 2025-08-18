package com.antsfamily.data

import com.antsfamily.data.local.SettingsStore
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsStore: SettingsStore,
) : SettingsRepository {

    override fun getIsPinCreated(): Boolean {
        return settingsStore.getIsPinCreated()
    }

    override fun setIsPinCreated(isCreated: Boolean) {
        settingsStore.setIsPinCreated(isCreated)
    }

    override fun getIsDarkMode(): Boolean {
        return settingsStore.getDarkModeEnabled()
    }

    override fun setIsDarkMode(isDarkMode: Boolean) {
        settingsStore.setDarkModeEnabled(isDarkMode)
    }
}