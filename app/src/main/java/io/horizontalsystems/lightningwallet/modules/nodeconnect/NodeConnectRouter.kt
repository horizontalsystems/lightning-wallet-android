package io.horizontalsystems.lightningwallet.modules.nodeconnect

import io.horizontalsystems.core.SingleLiveEvent

class NodeConnectRouter : NodeConnectModule.INodeConnectRouter {
    val openMainModule = SingleLiveEvent<Unit>()

    override fun openMainModule() {
        openMainModule.postValue(Unit)
    }
}
