package io.horizontalsystems.lightningwallet.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.horizontalsystems.lightningwallet.entities.LightningConnection
import io.horizontalsystems.lightningwallet.entities.Wallet

class WalletStorage(private val preferences: SharedPreferences) {

    private val connectionKey = "connection"
    private val gson by lazy {
        Gson()
    }

    var storedWallet: Wallet?
        get() {
            val data = preferences.getString(connectionKey, null) ?: return null
            val json = gson.fromJson(data, JsonObject::class.java)

            val credentials = json.get("credentials")
            val connection = if (credentials != null) {
                gson.fromJson(data, LightningConnection.Remote::class.java)
            } else {
                LightningConnection.Local
            }

            return Wallet(connection)
        }
        set(value) {
            if (value == null) {
                preferences.edit().remove(connectionKey).apply()
            } else {
                preferences.edit().putString(connectionKey, gson.toJson(value.connection)).apply()
            }
        }
}
