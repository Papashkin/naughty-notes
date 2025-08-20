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
        it.putBoolean(KEY_PROFILE_IS_DARK_MODE, isEnabled)
    }

    fun getDarkModeEnabled(): Boolean =
        sharedPrefs.getPrefs().getBoolean(KEY_PROFILE_IS_DARK_MODE, false)

    companion object {
        private const val KEY_PROFILE_IS_DARK_MODE = "key_profile_is_dark_mode"
    }
}
