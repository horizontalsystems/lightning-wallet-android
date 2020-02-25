package io.horizontalsystems.lightningwallet.modules.send.inputform

import io.horizontalsystems.core.SingleLiveEvent
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials

class InputFormRouter: InputFormModule.IRouter {
    val openConnectNode = SingleLiveEvent<RemoteLndCredentials>()

    override fun openConnectNode(credentials: RemoteLndCredentials) {
        openConnectNode.postValue(credentials)
    }
}
