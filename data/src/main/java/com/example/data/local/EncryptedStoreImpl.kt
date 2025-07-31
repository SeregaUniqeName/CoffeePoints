package com.example.data.local

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import jakarta.inject.Inject
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class EncryptedStoreImpl @Inject constructor() : EncryptedStore {

    override fun createKey(alias: String) {

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        keyGenerator.apply {
            init(
                KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
        }

        keyGenerator.generateKey()
    }

    override fun encrypt(input: String, alias: String): String {
        val key = getKey(alias)
        val cipher = Cipher.getInstance(CIPHER_PARAMS)
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val iv = cipher.iv
        val encryption = cipher.doFinal(input.toByteArray())

        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
        val encryptedString = Base64.encodeToString(encryption, Base64.DEFAULT)

        return "$ivString:$encryptedString"
    }

    override fun decrypt(input: String, alias: String): String {
        val parts = input.split(":")
        val iv = Base64.decode(parts[0], Base64.DEFAULT)
        val encrypted = Base64.decode(parts[1], Base64.DEFAULT)

        val key = getKey(alias)
        val cipher = Cipher.getInstance(CIPHER_PARAMS)
        val gcmParameterSpec = GCMParameterSpec(128, iv)

        cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec)

        val decryptedBytes = cipher.doFinal(encrypted)

        return String(decryptedBytes)
    }

    private fun getKey(alias: String): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null)

        return keyStore.getKey(alias, null) as SecretKey
    }

    companion object {
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private const val CIPHER_PARAMS = "AES/GCM/NoPadding"
    }
}