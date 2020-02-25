package io.horizontalsystems.lightningwallet.modules.send.scanaddress

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.integration.android.IntentIntegrator
import io.horizontalsystems.lightningwallet.helpers.TextHelper

object ScanAddressModule {

    interface IView {
        fun showEmptyClipboardError()
        fun showInvalidAddressError()
        fun startScanner()
    }

    interface IViewDelegate {
        fun onPaste()
        fun onScan(text: String)
        fun resetInput()
    }

    interface IInteractor {
        fun getPastedText(): String
    }

    interface IRouter {
        fun openSendForm(address: String)
    }

    fun start(context: Activity) {
        val intentIntegrator = IntentIntegrator(context)
        intentIntegrator.captureActivity = ScanAddressActivity::class.java
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.setPrompt("")
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        intentIntegrator.initiateScan()
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val router = ScanAddressRouter()
            val view = ScanAddressView()
            val interactor = ScanAddressInteractor(TextHelper)
            val presenter = ScanAddressPresenter(view, router, interactor)

            return presenter as T
        }
    }
}
