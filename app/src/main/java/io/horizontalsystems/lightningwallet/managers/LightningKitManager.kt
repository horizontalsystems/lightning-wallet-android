package io.horizontalsystems.lightningwallet.managers

import io.horizontalsystems.lightningkit.LightningKit
import io.horizontalsystems.lightningwallet.entities.LightningConnection

class LightningKitManager {

    var currentKit: LightningKit? = null
        private set

    fun loadKit(connection: LightningConnection) {
        when (connection) {
            is LightningConnection.Local -> {
            }
            is LightningConnection.Remote -> {
                currentKit = LightningKit.remote(connection.credentials)
            }
        }
    }

    fun unloadKit() {
        currentKit = null
    }
}
