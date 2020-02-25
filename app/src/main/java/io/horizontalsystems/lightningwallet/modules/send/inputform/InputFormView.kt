package io.horizontalsystems.lightningwallet.modules.send.inputform

import io.horizontalsystems.core.SingleLiveEvent

class InputFormView: InputFormModule.IView {

    val startScanner = SingleLiveEvent<Unit>()


    override fun startScanner() {
        startScanner.call()
    }
}
