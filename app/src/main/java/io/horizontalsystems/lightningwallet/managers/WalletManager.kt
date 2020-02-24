package io.horizontalsystems.lightningwallet.managers

import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials
import io.horizontalsystems.lightningwallet.entities.LightningConnection
import io.horizontalsystems.lightningwallet.entities.Wallet
import io.horizontalsystems.lightningwallet.storage.WalletStorage

class WalletManager(private val walletStorage: WalletStorage, private val lightningKitManager: LightningKitManager) {

    val hasStoredWallet: Boolean
        get() = walletStorage.storedWallet != null

    fun bootstrapStoredWallet() {
        walletStorage.storedWallet?.let {
            lightningKitManager.loadKit(it.connection)
        }
    }

    fun saveAndBootstrapRemoteWallet(credentials: RemoteLndCredentials) {
        val connection = LightningConnection.Remote(credentials)
        val wallet = Wallet(connection)

        walletStorage.storedWallet = wallet
        lightningKitManager.loadKit(connection)
    }
}
