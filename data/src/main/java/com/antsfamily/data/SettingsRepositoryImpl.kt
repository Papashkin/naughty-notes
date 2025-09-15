package com.antsfamily.data

import com.antsfamily.data.local.AppVersionSource
import com.antsfamily.data.local.SettingsStore
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsStore: SettingsStore,
    private val appVersionSource: AppVersionSource
) : SettingsRepository {

    override fun setPinCode(code: String) {
        return settingsStore.setPinCode(code)
    }

    override fun removePinCode() {
        return settingsStore.removePinCode()
    }

    override fun isPinCodeSet(): Boolean {
        return settingsStore.isPinCodeSet()
    }

    override fun verifyPinCode(code: String): Boolean {
        return settingsStore.verifyPinCode(code)
    }

    override fun getIsDarkMode(): Boolean {
        return settingsStore.getDarkModeEnabled()
    }

    override fun setIsDarkMode(isDarkMode: Boolean) {
        settingsStore.setDarkModeEnabled(isDarkMode)
    }

    override fun invalidateLockTimestamp() {
        settingsStore.invalidateLockTimestamp()
    }

    override fun setLockTimestamp(timestamp: Long) {
        settingsStore.setLockTimestamp(timestamp)
    }

    override fun getLockTimestamp(): Long {
        return settingsStore.getLockTimestamp()
    }

    override fun getAppVersion(): String? {
        return appVersionSource.getAppVersion()
    }
}