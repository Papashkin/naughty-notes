package com.antsfamily.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPrefs @Inject constructor(context: Context) {

    private val prefsName = "com.antsfamily.data.local.shared_prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun editAndCommit(operation: (SharedPreferences.Editor) -> Unit): Boolean =
        with(prefs.edit()) {
            operation(this)
            commit()
        }

    fun clearAll() {
        prefs.edit { clear() }
    }

    fun getPrefs(): SharedPreferences = prefs
}
