package io.horizontalsystems.lightningwallet.modules.main

import io.horizontalsystems.core.SingleLiveEvent

class MainView : MainModule.IView {

    val showSyncingText = SingleLiveEvent<String>()
    val hideSyncingText = SingleLiveEvent<Unit>()
    val showBalance = SingleLiveEvent<Int>()

    override fun showConnectingStatus() {
        showSyncingText.postValue("Connecting... ")
    }

    override fun showSyncingStatus() {
        showSyncingText.postValue("Syncing... ")
    }

    override fun showErrorStatus(e: Throwable) {
        showSyncingText.postValue(e.message)
    }

    override fun hideStatus() {
        hideSyncingText.postValue(Unit)
    }

    override fun show(totalBalance: Int) {
        showBalance.postValue(totalBalance)
    }
}
