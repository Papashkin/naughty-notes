package com.antsfamily.domain.repository

interface SettingsRepository {
    fun getPinCode(): Int?
    fun setPinCode(code: Int)
    fun getIsDarkMode(): Boolean
    fun setIsDarkMode(isDarkMode: Boolean)
}