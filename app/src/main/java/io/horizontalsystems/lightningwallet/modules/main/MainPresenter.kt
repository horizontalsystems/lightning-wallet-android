package io.horizontalsystems.lightningwallet.modules.main

import androidx.lifecycle.ViewModel
import io.horizontalsystems.lightningkit.ILndNode.Status

class MainPresenter(
        val view: MainModule.IView,
        private val interactor: MainModule.IInteractor)
    : MainModule.IViewDelegate, MainModule.IInteractorDelegate, ViewModel() {

    private var walletBalance: Int? = null
    private var channelBalance: Int? = null

    //  IViewDelegate

    override fun onLoad() {
        interactor.subscribeToStatus()

        interactor.fetchWalletBalance()
        interactor.fetchChannelBalance()
    }

    //  IInteractorDelegate

    override fun onUpdate(status: Status) {
        when (status) {
            is Status.CONNECTING -> {
                view.showConnectingStatus()
            }
            is Status.SYNCING -> {
                view.showSyncingStatus()
            }
            is Status.ERROR -> {
                view.showErrorStatus(status.throwable)
            }
            is Status.RUNNING -> {
                view.hideStatus()
            }
            else -> {
                view.hideStatus()
            }
        }
    }

    override fun onUpdateWallet(balance: Int) {
        walletBalance = balance
        syncBalance()
    }

    override fun onUpdateChannel(balance: Int) {
        channelBalance = balance
        syncBalance()
    }

    private fun syncBalance() {
        var totalBalance = 0

        channelBalance?.let {
            totalBalance += it
        }

        walletBalance?.let {
            totalBalance += it
        }

        view.show(totalBalance)
    }

    //  ViewModel

    override fun onCleared() {
        interactor.clear()
    }
}
