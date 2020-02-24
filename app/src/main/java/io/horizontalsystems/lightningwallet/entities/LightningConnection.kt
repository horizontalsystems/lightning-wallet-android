package io.horizontalsystems.lightningwallet.entities

import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials

sealed class LightningConnection {
    object Local : LightningConnection()
    class Remote(val credentials: RemoteLndCredentials) : LightningConnection()
}
