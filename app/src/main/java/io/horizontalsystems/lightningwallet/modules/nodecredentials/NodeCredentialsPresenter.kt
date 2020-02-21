package io.horizontalsystems.lightningwallet.modules.nodecredentials

import android.util.Log
import androidx.lifecycle.ViewModel
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials

class NodeCredentialsPresenter(
    val view: NodeCredentialsModule.IView,
    val router: NodeCredentialsModule.IRouter,
    private val interactor: NodeCredentialsModule.IInteractor
) : ViewModel(), NodeCredentialsModule.IViewDelegate {

    override fun viewDidLoad() {
        view.showDescription()
    }

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
        view.showDescription()
        view.startScanner()
    }

    private fun process(text: String) {
        val credentials: RemoteLndCredentials? = try {
            interactor.getCredentials(text)
        } catch (e: Exception){
            Log.e("NodeCredentialsPresenter", "process exception", e)
            null
        }

        if (credentials != null) {
            view.showDescription()
            router.openConnectNode(credentials)
        } else {
            view.showInvalidAddressError()
        }
    }
}
