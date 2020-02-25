package io.horizontalsystems.lightningwallet.modules.send.scanaddress

import androidx.lifecycle.ViewModel

class ScanAddressPresenter(
    val view: ScanAddressModule.IView,
    val router: ScanAddressModule.IRouter,
    private val interactor: ScanAddressModule.IInteractor
) : ViewModel(), ScanAddressModule.IViewDelegate {

    override fun onPaste() {
        val pastedText = interactor.getPastedText()
        if (pastedText.isEmpty()){
            view.showEmptyClipboardError()
        } else {
            process(pastedText)
        }
    }

    override fun onScan(text: String) {
        process(text)
    }

    override fun resetInput() {
        view.startScanner()
    }

    private fun process(address: String) {
        if (address.isNullOrEmpty()) {
            view.showInvalidAddressError()
        } else {
            router.openSendForm(address)
        }
    }
}
