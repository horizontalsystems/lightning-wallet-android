package io.horizontalsystems.lightningwallet.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.horizontalsystems.core.entities.AppVersion
import io.horizontalsystems.lightningwallet.ILocalStorage

class LocalStorage(private val preferences: SharedPreferences) : ILocalStorage {

    private val iUnderstandKey = "i_understand"
    private val baseCurrencyKey = "base_currency_code"
    private val appVersionKey = "app_versions"
    private val notificationKey = "alert_notification"
    private val encryptionCheckerKey = "encryption_checker_text"

    private val gson by lazy { Gson() }

    override var iUnderstand: Boolean
        get() = preferences.getBoolean(iUnderstandKey, false)
        set(value) {
            preferences.edit().putBoolean(iUnderstandKey, value).apply()
        }

    override var baseCurrencyCode: String?
        get() = preferences.getString(baseCurrencyKey, null)
        set(value) {
            preferences.edit().putString(baseCurrencyKey, value).apply()
        }

    override var appVersions: List<AppVersion>
        get() {
            val versionsString = preferences.getString(appVersionKey, null) ?: return listOf()
            val type = object : TypeToken<ArrayList<AppVersion>>() {}.type
            return gson.fromJson(versionsString, type)
        }
        set(value) {
            val versionsString = gson.toJson(value)
            preferences.edit().putString(appVersionKey, versionsString).apply()
        }

    override var isAlertNotificationOn: Boolean
        get() = preferences.getBoolean(notificationKey, true)
        set(enabled) {
            preferences.edit().putBoolean(notificationKey, enabled).apply()
        }

    override var encryptedSampleText: String?
        get() = preferences.getString(encryptionCheckerKey, null)
        set(encryptedText) {
            preferences.edit().putString(encryptionCheckerKey, encryptedText).apply()
        }

    override fun clear() {
        preferences.edit().clear().apply()
    }
}
