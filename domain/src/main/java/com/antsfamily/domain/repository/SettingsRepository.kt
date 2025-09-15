package com.antsfamily.domain.repository

interface SettingsRepository {
    fun setPinCode(code: String)
    fun removePinCode()
    fun isPinCodeSet(): Boolean
    fun verifyPinCode(code: String): Boolean
    fun getIsDarkMode(): Boolean
    fun setIsDarkMode(isDarkMode: Boolean)
    fun invalidateLockTimestamp()
    fun setLockTimestamp(timestamp: Long)
    fun getLockTimestamp(): Long
    fun getAppVersion(): String?
}