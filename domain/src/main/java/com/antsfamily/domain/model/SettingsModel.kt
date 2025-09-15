package com.antsfamily.domain.model

data class SettingsModel(
    val isPinCodeSet: Boolean,
    val isDarkMode: Boolean,
    val appVersion: String?
)
