package io.horizontalsystems.lightningwallet.modules.main

import io.horizontalsystems.lightningkit.LightningKit
import io.reactivex.disposables.CompositeDisposable

class MainInteractor(private val lightningKit: LightningKit?) : MainModule.IInteractor {

    var delegate: MainModule.IInteractorDelegate? = null

    private var disposables = CompositeDisposable()

    override fun subscribeToStatus() {
        lightningKit
                ?.statusObservable
                ?.subscribe {
                    delegate?.onUpdate(it)
                }
                ?.let {
                    disposables.add(it)
                }
    }

    override fun fetchWalletBalance() {
        lightningKit
                ?.getWalletBalance()
                ?.subscribe({
                    delegate?.onUpdateWallet(it.totalBalance.toInt())
                }, {

                })
                ?.let {
                    disposables.add(it)
                }
    }

    override fun fetchChannelBalance() {
        lightningKit
                ?.getChannelBalance()
                ?.subscribe({
                    delegate?.onUpdateChannel(it.balance.toInt())
                }, {

                })
                ?.let {
                    disposables.add(it)
                }
    }

    override fun subscribeToWalletBalance() {
        lightningKit
                ?.walletBalanceObservable
                ?.subscribe({
                    delegate?.onUpdateWallet(it.totalBalance.toInt())
                }, {

                })
                ?.let {
                    disposables.add(it)
                }
    }

    override fun subscribeToChannelBalance() {
        lightningKit
                ?.channelBalanceObservable
                ?.subscribe({
                    delegate?.onUpdateChannel(it.balance.toInt())
                }, {

                })
                ?.let {
                    disposables.add(it)
                }
    }

    override fun clear() {
        disposables.dispose()
    }
}
