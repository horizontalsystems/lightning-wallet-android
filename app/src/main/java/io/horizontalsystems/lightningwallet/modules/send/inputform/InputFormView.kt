package io.horizontalsystems.lightningwallet.modules.send.inputform

import androidx.lifecycle.MutableLiveData
import io.horizontalsystems.core.SingleLiveEvent

class InputFormView: InputFormModule.IView {

    val startScanner = SingleLiveEvent<Unit>()
    val addressValue = MutableLiveData<String>()

    override fun setAddress(address: String) {
        addressValue.postValue(address)
    }

    override fun startScanner() {
        startScanner.call()
    }
}
