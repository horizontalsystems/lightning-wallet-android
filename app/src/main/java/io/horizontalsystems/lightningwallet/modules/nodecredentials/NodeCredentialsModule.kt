package io.horizontalsystems.lightningwallet.modules.nodecredentials

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.integration.android.IntentIntegrator
import io.horizontalsystems.lightningkit.LndConnect
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials
import io.horizontalsystems.lightningwallet.helpers.TextHelper

object NodeCredentialsModule {

    interface IView{
        fun showDescription()
        fun showEmptyClipboardError()
        fun showInvalidAddressError()
        fun startScanner()
    }

    interface IViewDelegate {
        fun viewDidLoad()
        fun onPaste()
        fun onScan(text: String)
        fun resetInput()
    }

    interface IInteractor {
        fun getPastedText(): String
        fun getCredentials(text: String): RemoteLndCredentials
    }

    interface IRouter {
        fun openConnectNode(credentials: RemoteLndCredentials)
    }

    fun start(context: Activity) {
        val intentIntegrator = IntentIntegrator(context)
        intentIntegrator.captureActivity = NodeCredentialsActivity::class.java
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.setPrompt("")
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        intentIntegrator.initiateScan()
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val router = NodeCredentialsRouter()
            val view = NodeCredentialsView()
            val interactor = NodeCredentialsInteractor(TextHelper, LndConnect)
            val presenter = NodeCredentialsPresenter(view, router, interactor)

            return presenter as T
        }
    }
}
