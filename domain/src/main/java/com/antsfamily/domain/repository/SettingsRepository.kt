package com.antsfamily.domain.repository

interface SettingsRepository {
    fun getIsPinCreated(): Boolean
    fun setIsPinCreated(isCreated: Boolean)
    fun getIsDarkMode(): Boolean
    fun setIsDarkMode(isDarkMode: Boolean)
}