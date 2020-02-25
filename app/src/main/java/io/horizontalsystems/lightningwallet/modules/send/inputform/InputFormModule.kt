package io.horizontalsystems.lightningwallet.modules.send.inputform

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials

object InputFormModule {

    interface IView {
        fun startScanner()
        fun setAddress(address: String)
    }

    interface IViewDelegate {
        fun viewDidLoad()
    }

    interface IInteractor {
    }

    interface IRouter {
        fun openConnectNode(credentials: RemoteLndCredentials)
    }

    fun start(context: Context, address: String) {
        val intent = Intent(context, InputFormActivity::class.java).apply {
            putExtra(InputFormActivity.AddressKey, address)
        }

        context.startActivity(intent)
    }

    class Factory(private val address: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val router = InputFormRouter()
            val view = InputFormView()
            val interactor = InputFormInteractor()
            val presenter = InputFormPresenter(view, router, interactor, address)

            return presenter as T
        }
    }
}
