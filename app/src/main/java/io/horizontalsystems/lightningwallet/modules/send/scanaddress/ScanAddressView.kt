package io.horizontalsystems.lightningwallet.modules.send.scanaddress

import io.horizontalsystems.core.SingleLiveEvent

class ScanAddressView: ScanAddressModule.IView {

    val emptyClipboardError = SingleLiveEvent<Unit>()
    val invalidAddressError = SingleLiveEvent<Unit>()
    val startScanner = SingleLiveEvent<Unit>()

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
