package io.horizontalsystems.lightningwallet.modules.nodecredentials

import io.horizontalsystems.core.SingleLiveEvent
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials

class NodeCredentialsRouter: NodeCredentialsModule.IRouter {
    val openConnectNode = SingleLiveEvent<RemoteLndCredentials>()

    override fun openConnectNode(credentials: RemoteLndCredentials) {
        openConnectNode.postValue(credentials)
    }
}
