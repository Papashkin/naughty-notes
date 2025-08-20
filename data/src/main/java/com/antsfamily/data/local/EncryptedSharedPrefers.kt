package com.antsfamily.data.local

import android.content.Context
import android.util.Base64
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject

private const val SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256"
private const val SECRET_PIN_HASH = "pin_hash"
private const val SECRET_PIN_SALT = "pin_salt"

@Suppress("DEPRECATION")
class EncryptedSharedPrefers @Inject constructor(context: Context) {

    private val prefsName = "com.antsfamily.data.local.encrypted_shared_prefs"

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        prefsName,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun savePin(pin: String) {
        val salt = generateSalt()
        val hash = hashPin(pin, salt)
        prefs.edit {
            putString(SECRET_PIN_HASH, hash)
                .putString(SECRET_PIN_SALT, Base64.encodeToString(salt, Base64.NO_WRAP))
        }
    }

    private fun generateSalt(): ByteArray {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        return salt
    }

    private fun hashPin(pin: String, salt: ByteArray): String {
        val spec = PBEKeySpec(pin.toCharArray(), salt, 10000, 256)
        val factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
        val hash = factory.generateSecret(spec).encoded
        return Base64.encodeToString(hash, Base64.NO_WRAP)
    }

    fun verifyPin(code: String): Boolean {
        val saltBase64 = prefs.getString(SECRET_PIN_SALT, null) ?: return false
        val hashStored = prefs.getString(SECRET_PIN_HASH, null) ?: return false
        val salt = Base64.decode(saltBase64, Base64.NO_WRAP)
        val hashInput = hashPin(code, salt)
        return hashInput == hashStored
    }

    fun isPinSet(): Boolean {
        val hash = prefs.getString(SECRET_PIN_HASH, null)
        val salt = prefs.getString(SECRET_PIN_SALT, null)
        return !hash.isNullOrEmpty() && !salt.isNullOrEmpty()
    }

    fun clearAll() {
        prefs.edit { clear() }
    }
}