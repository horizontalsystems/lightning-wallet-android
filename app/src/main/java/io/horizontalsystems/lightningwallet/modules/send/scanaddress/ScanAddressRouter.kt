package io.horizontalsystems.lightningwallet.modules.send.scanaddress

import io.horizontalsystems.core.SingleLiveEvent

class ScanAddressRouter: ScanAddressModule.IRouter {
    val openSendForm = SingleLiveEvent<String>()

    override fun openSendForm(address: String) {
        openSendForm.postValue(address)
    }
}
