package com.antsfamily.data.local

import javax.inject.Inject

class SettingsStore @Inject constructor(
    private val sharedPrefs: SharedPrefs,
    private val securePrefs: EncryptedSharedPrefers
) {

    fun clearAll() {
        securePrefs.clearAll()
        sharedPrefs.clearAll()
    }

    fun isPinCodeSet(): Boolean {
        return securePrefs.isPinSet()
    }

    fun verifyPinCode(code: String): Boolean {
        return securePrefs.verifyPin(code)
    }

    fun removePinCode() {
        return securePrefs.clearAll()
    }

    fun setPinCode(code: String) = securePrefs.savePin(code)

    fun setDarkModeEnabled(isEnabled: Boolean) = sharedPrefs.editAndCommit {
        it.putBoolean(KEY_SETTINGS_IS_DARK_MODE, isEnabled)
    }

    fun getDarkModeEnabled(): Boolean =
        sharedPrefs.getPrefs().getBoolean(KEY_SETTINGS_IS_DARK_MODE, false)

    fun setLockTimestamp(timestamp: Long) = sharedPrefs.editAndCommit {
        it.putLong(KEY_SETTINGS_LOCK_TIMESTAMP, timestamp)
    }

    fun invalidateLockTimestamp() = sharedPrefs.editAndCommit {
        it.putLong(KEY_SETTINGS_LOCK_TIMESTAMP, 0L)
    }

    fun getLockTimestamp(): Long = sharedPrefs
        .getPrefs()
        .getLong(KEY_SETTINGS_LOCK_TIMESTAMP, 0L)

    companion object {
        private const val KEY_SETTINGS_IS_DARK_MODE = "key_settings_is_dark_mode"
        private const val KEY_SETTINGS_LOCK_TIMESTAMP = "key_settings_lock_timestamp"
    }
}
