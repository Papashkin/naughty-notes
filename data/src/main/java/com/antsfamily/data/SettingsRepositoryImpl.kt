package com.antsfamily.data

import com.antsfamily.data.local.SettingsStore
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsStore: SettingsStore,
) : SettingsRepository {
    override fun getPinCode(): Int? =
        settingsStore.getPinCode()

    override fun setPinCode(code: Int) {
        settingsStore.setPinCode(code)
    }

    override fun getIsDarkMode(): Boolean {
        return settingsStore.getDarkModeEnabled()
    }

    override fun setIsDarkMode(isDarkMode: Boolean) {
        settingsStore.setDarkModeEnabled(isDarkMode)
    }
}