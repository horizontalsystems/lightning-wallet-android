package io.horizontalsystems.lightningwallet.modules.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningkit.ILndNode
import io.horizontalsystems.lightningwallet.App

object MainModule {

    interface IView {
        fun showConnectingStatus()
        fun showSyncingStatus()
        fun showErrorStatus(e: Throwable)
        fun hideStatus()
        fun show(totalBalance: Int)
    }

    interface IViewDelegate {
        fun onLoad()
    }

    interface IInteractor {
        fun subscribeToStatus()
        fun fetchWalletBalance()
        fun fetchChannelBalance()
        fun subscribeToWalletBalance()
        fun subscribeToChannelBalance()
        fun clear()
    }

    interface IInteractorDelegate {
        fun onUpdate(status: ILndNode.Status)
        fun onUpdateWallet(balance: Int)
        fun onUpdateChannel(balance: Int)
    }

    interface IRouter

    fun start(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        context.startActivity(intent)
    }

    fun startAsNewTask(context: Activity) {
        start(context)
        context.overridePendingTransition(0, 0)
    }

    class Factory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val view = MainView()
            val interactor = MainInteractor(App.lightningKitManager.currentKit)
            val presenter = MainPresenter(view, interactor)

            interactor.delegate = presenter

            return presenter as T
        }
    }
}
