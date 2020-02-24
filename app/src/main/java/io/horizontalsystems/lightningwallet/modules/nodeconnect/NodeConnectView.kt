package io.horizontalsystems.lightningwallet.modules.nodeconnect

import io.horizontalsystems.core.SingleLiveEvent

class NodeConnectView : NodeConnectModule.INodeConnectView {

    val showAddress = SingleLiveEvent<String>()
    val showConnecting = SingleLiveEvent<Unit>()
    val hideConnecting = SingleLiveEvent<Unit>()
    val showError = SingleLiveEvent<String>()

    override fun show(address: String) {
        showAddress.postValue(address)
    }

    override fun showConnecting() {
        showConnecting.postValue(Unit)
    }

    override fun hideConnecting() {
        hideConnecting.postValue(Unit)
    }

    override fun showError(e: Throwable) {
        showError.postValue(e.message)
    }
}
