package io.horizontalsystems.lightningwallet.modules.nodecredentials

import io.horizontalsystems.core.SingleLiveEvent

class NodeCredentialsView: NodeCredentialsModule.IView {

    val showDescription = SingleLiveEvent<Unit>()
    val emptyClipboardError = SingleLiveEvent<Unit>()
    val invalidAddressError = SingleLiveEvent<Unit>()
    val startScanner = SingleLiveEvent<Unit>()

    override fun showDescription() {
        showDescription.call()
    }

    override fun showEmptyClipboardError() {
        emptyClipboardError.call()
    }

    override fun showInvalidAddressError() {
        invalidAddressError.call()
    }

    override fun startScanner() {
        startScanner.call()
    }
}
