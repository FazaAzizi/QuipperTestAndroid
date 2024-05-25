package com.faza.quippertest.common

import android.annotation.SuppressLint
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.Key
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.EncodedKeySpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Encryptions {
    private const val ALGORITHM_AES_CIPHER: String = "AES/GCM/NoPadding"
    private const val ALGORITHM_RSA_CIPHER: String = "RSA/ECB/PKCS1Padding"
    private const val AES_KEY: String = "AES"
    private const val RSA_KEY: String = "RSA"
    private const val DES3_KEY: String = "DES3"
    private const val ALGORITHM_CIPHER = "DESede/ECB/PKCS5Padding"
    private const val SECURITY_PROVIDER = "MPIN"

    private fun getKeyAsBytes(key: Key): ByteArray? {
        return key.encoded
    }

    fun getKeyAES(key: Key): String {
        val flags = Base64.URL_SAFE or Base64.NO_WRAP
        return Base64.encodeToString(getKeyAsBytes(key), flags)
    }

    fun generateKeyAES(): Key {
        val keygen = KeyGenerator.getInstance(AES_KEY)
        keygen.init(256)
        return keygen.generateKey()
    }

    /*Encrypt*/
    fun encryptData(dataToEncrypt: String, key: Key): String {
        val cipherText = encryptAES(dataToEncrypt, key)
        return cipherText.toString()
    }

    /*Encrypt with RSA*/
    fun encryptDataWithRSA(dataToEncrypt: String, key: PublicKey?): String {
        return encryptRSAWithPublicKey(dataToEncrypt, key)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class,
        InvalidAlgorithmParameterException::class
    )
    fun encryptAES(plain: ByteArray?, key: Key?): ByteArray? {
        val cipher: Cipher = Cipher.getInstance(ALGORITHM_AES_CIPHER)
        val iv = ByteArray(16)
        val ivSpec: AlgorithmParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)
        return cipher.doFinal(plain)
    }

    @Throws(
        NoSuchPaddingException::class,
        IllegalBlockSizeException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        InvalidAlgorithmParameterException::class
    )
    fun encryptAES(plainString: String, key: Key?): String? {
        val plainBytes: ByteArray = plainString.toByteArray(StandardCharsets.UTF_8)
        val encrypted = encryptAES(plainBytes, key)
        val flags = Base64.URL_SAFE or Base64.NO_WRAP
        return Base64.encodeToString(encrypted, flags)
    }

    /*Decrypt*/
    private fun getKey(keyBytes: ByteArray): Key {
        val key: Key
        key = SecretKeySpec(keyBytes, AES_KEY)
        return key
    }

    fun getKeyFromBase64(base64: String?): Key {
        val flags = Base64.URL_SAFE or Base64.NO_WRAP
        val keyBytes: ByteArray = Base64.decode(base64, flags)
        return getKey(keyBytes)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class,
        InvalidAlgorithmParameterException::class
    )
    fun decryptAES(encryptedData: ByteArray?, key: Key?): ByteArray? {
        val cipher: Cipher = Cipher.getInstance(ALGORITHM_AES_CIPHER)
        val iv = ByteArray(16)
        val ivSpec: AlgorithmParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
        return cipher.doFinal(encryptedData)
    }

    @Throws(
        NoSuchPaddingException::class,
        IllegalBlockSizeException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        InvalidAlgorithmParameterException::class
    )
    fun decryptAES(base64Encrypted: String?, key: Key?): String {
        val flags = Base64.URL_SAFE or Base64.NO_WRAP
        val encrypted: ByteArray = Base64.decode(base64Encrypted, flags)
        val plainBytes = decryptAES(encrypted, key)
        return String(plainBytes!!)
    }

    fun decryptData(dataToDecrypt: String, key: Key?): String {
        return decryptAES(dataToDecrypt, key)
    }

    /*RSA*/
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPublicKeyFromString(key: String?): PublicKey? {
        val keyFactory = KeyFactory.getInstance(RSA_KEY)
        val flags = Base64.URL_SAFE or Base64.NO_WRAP
        val publicKeySpec: EncodedKeySpec = X509EncodedKeySpec(Base64.decode(key, flags))
        return keyFactory.generatePublic(publicKeySpec)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun encryptRSA(plain: ByteArray?, key: Key?): ByteArray? {
        val cipher: Cipher = Cipher.getInstance(ALGORITHM_RSA_CIPHER)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(plain)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun encryptRSAPrivateKey(plain: ByteArray?, key: PrivateKey?): ByteArray? {
        return encryptRSA(plain, key)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun decryptRSA(encryptedData: ByteArray?, key: Key?): ByteArray? {
        val cipher: Cipher = Cipher.getInstance(ALGORITHM_RSA_CIPHER)
        cipher.init(Cipher.DECRYPT_MODE, key)
        return cipher.doFinal(encryptedData)
    }

    @Throws(
        NoSuchPaddingException::class,
        IllegalBlockSizeException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class
    )
    fun encryptRSAWithPublicKey(plainString: String, key: PublicKey?): String {
        val plainBytes = plainString.toByteArray(StandardCharsets.UTF_8)
        val encrypted = encryptRSA(plainBytes, key)
        val flags = Base64.URL_SAFE or Base64.NO_WRAP
        return Base64.encodeToString(encrypted, flags)
    }

    @Throws(
        NoSuchPaddingException::class,
        IllegalBlockSizeException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class
    )

    fun decryptRSA(base64Encrypted: String?, key: Key?): String {
        val flags = Base64.URL_SAFE or Base64.NO_WRAP
        val encrypted: ByteArray = Base64.decode(base64Encrypted, flags)
        val plainBytes = decryptRSA(encrypted, key)
        return String(plainBytes!!)
    }

    /*
    @Throws(
        NoSuchPaddingException::class,
        IllegalBlockSizeException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class
    )

    fun encryptPin(data: String?): String? {
        val transportKey: Key = getKeyMBankingDes3(transportKey)
        val encryptedBytes: ByteArray = Base64.decode(encryptedKey, Base64.DEFAULT)
        val masterKeyBytes: ByteArray = decryptDES3(encryptedBytes, transportKey)
        val masterKey = getKeyDES3(masterKeyBytes)
        return data?.let { encryptDES3(it, masterKey) }
    }
    */

    @Throws(
        NoSuchPaddingException::class,
        IllegalBlockSizeException::class,
        NoSuchAlgorithmException::class,
        BadPaddingException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class
    )
    fun encryptDES3(plainString: String, key: Key?): String {
        val plainBytes = plainString.toByteArray(StandardCharsets.UTF_8)
        val encrypted: ByteArray = encryptDES3(plainBytes, key)
        return String(Base64.encode(encrypted, Base64.DEFAULT))
    }

    @SuppressLint("GetInstance")
    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun encryptDES3(plain: ByteArray?, key: Key?): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM_CIPHER, SECURITY_PROVIDER)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(plain)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun decryptDES3(encryptedData: ByteArray?, key: Key?): ByteArray {
        val cipher: Cipher = Cipher.getInstance(ALGORITHM_CIPHER, SECURITY_PROVIDER)
        cipher.init(Cipher.DECRYPT_MODE, key)
        return cipher.doFinal(encryptedData)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun getKeyMBankingDes3(keyString: String): Key {
        val keyBytes: ByteArray = normalizeLengthKeyForDES3(keyString)
        return getKeyDES3(keyBytes)
    }

    private fun normalizeLengthKeyForDES3(keyString: String?): ByteArray {
        val keyStrings = "$keyString                        "
        return keyStrings.substring(0, 24).toByteArray(StandardCharsets.ISO_8859_1)
    }

    private fun getKeyDES3(keyBytes: ByteArray): Key {
        val key: Key
        key = SecretKeySpec(keyBytes, DES3_KEY)
        return key
    }

}