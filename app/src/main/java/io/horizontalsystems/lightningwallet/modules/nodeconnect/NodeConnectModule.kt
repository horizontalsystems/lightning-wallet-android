package io.horizontalsystems.lightningwallet.modules.nodeconnect

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials

object NodeConnectModule {

    interface INodeConnectRouter {
        fun openMainModule()
    }

    interface INodeConnectView {
        fun show(address: String)
        fun showConnecting()
        fun hideConnecting()
        fun showError(e: Throwable)
    }

    interface INodeConnectViewDelegate {
        fun onLoad()
        fun onClickConnect()
    }

    interface INodeConnectInteractor {
        fun validate(credentials: RemoteLndCredentials)
        fun saveWallet(credentials: RemoteLndCredentials)
    }

    interface INodeConnectInteractorDelegate {
        fun onValidateCredentials()
        fun onFailToValidateCredentials(e: Throwable)
    }

    fun start(context: Context, credentials: RemoteLndCredentials) {
        val intent = Intent(context, NodeConnectActivity::class.java).apply {
            putExtra("credentials", credentials)
        }

        context.startActivity(intent)
    }

    class Factory(private val credentials: RemoteLndCredentials) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val view = NodeConnectView()
            val router = NodeConnectRouter()
            val interactor = NodeConnectInteractor()

            val presenter = NodeConnectPresenter(view, router, interactor, credentials).apply {
                interactor.delegate = this
            }

            return presenter as T
        }
    }
}
