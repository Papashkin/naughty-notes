package com.antsfamily.data.local

import javax.inject.Inject

class SettingsStore @Inject constructor(
    private val sharedPrefs: SharedPrefs
) {

    fun clearAll() = sharedPrefs.clearAll()

    fun getPinCode(): Int? {
        val code = sharedPrefs.getPrefs().getInt(KEY_PROFILE_PIN_CODE, 0)
        return if (code == 0) null else code
    }

    fun setPinCode(code: Int) = sharedPrefs.editAndCommit {
        it.putInt(KEY_PROFILE_PIN_CODE, code)
    }

    fun setIsPinEnabled(isEnabled: Boolean) = sharedPrefs.editAndCommit {
        it.putBoolean(KEY_PROFILE_IS_PIN_ENABLED, isEnabled)
    }

    fun getIsPinEnabled(): Boolean =
        sharedPrefs.getPrefs().getBoolean(KEY_PROFILE_IS_PIN_ENABLED, false)


    fun setDarkModeEnabled(isEnabled: Boolean) = sharedPrefs.editAndCommit {
        it.putBoolean(KEY_PROFILE_IS_DARK_MODE, isEnabled)
    }

    fun getDarkModeEnabled(): Boolean =
        sharedPrefs.getPrefs().getBoolean(KEY_PROFILE_IS_DARK_MODE, false)

    companion object {
        private const val KEY_PROFILE_PIN_CODE = "key_profile_pin_code"
        private const val KEY_PROFILE_IS_PIN_ENABLED = "key_profile_is_pin_enabled"
        private const val KEY_PROFILE_IS_DARK_MODE = "key_profile_is_dark_mode"
    }
}
