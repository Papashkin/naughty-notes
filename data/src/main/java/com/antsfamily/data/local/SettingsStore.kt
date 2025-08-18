package com.antsfamily.data.local

import javax.inject.Inject

class SettingsStore @Inject constructor(
    private val sharedPrefs: SharedPrefs
) {

    fun clearAll() = sharedPrefs.clearAll()

    fun setIsPinCreated(isCreated: Boolean) = sharedPrefs.editAndCommit {
        it.putBoolean(KEY_PROFILE_IS_PIN_CREATED, isCreated)
    }

    fun getIsPinCreated(): Boolean =
        sharedPrefs.getPrefs().getBoolean(KEY_PROFILE_IS_PIN_CREATED, false)


    fun setDarkModeEnabled(isEnabled: Boolean) = sharedPrefs.editAndCommit {
        it.putBoolean(KEY_PROFILE_IS_DARK_MODE, isEnabled)
    }

    fun getDarkModeEnabled(): Boolean =
        sharedPrefs.getPrefs().getBoolean(KEY_PROFILE_IS_DARK_MODE, false)

    companion object {
        private const val KEY_PROFILE_IS_PIN_CREATED = "key_profile_is_pin_created"
        private const val KEY_PROFILE_IS_DARK_MODE = "key_profile_is_dark_mode"
    }
}
