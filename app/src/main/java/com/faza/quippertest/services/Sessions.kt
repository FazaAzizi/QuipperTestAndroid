package com.faza.quippertest.services

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.faza.quippertest.common.Constants

class Sessions(context: Context) {

    companion object {
        const val keyAlias: String = "_androidx_security_master_key_"
        const val secretSharedPref: String = "secret_shared_prefs"
    }

    private var editor: SharedPreferences.Editor
    private val spec = KeyGenParameterSpec.Builder(
        keyAlias,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .build()

    private val masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private val pref = EncryptedSharedPreferences.create(
        context,
        secretSharedPref,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    init {
        editor = pref.edit()
    }

}