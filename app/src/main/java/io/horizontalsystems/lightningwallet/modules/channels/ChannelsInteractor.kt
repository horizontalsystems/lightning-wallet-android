package io.horizontalsystems.lightningwallet.modules.channels

import io.horizontalsystems.lightningkit.LightningKit
import io.reactivex.disposables.CompositeDisposable

class ChannelsInteractor(private val lightningKit: LightningKit?) : ChannelsModule.IInteractor {

    var delegate: ChannelsModule.IInteractorDelegate? = null

    private val disposables = CompositeDisposable()

    override fun fetchOpenChannels() {
        lightningKit
                ?.listChannels()
                ?.subscribe({
                    delegate?.onUpdateOpenChannels(it.channelsList)
                }, {
                    println("")
                })
                ?.let { disposables.add(it) }
    }

    override fun fetchPendingChannels() {
        lightningKit
                ?.listPendingChannels()
                ?.subscribe({
                    delegate?.onUpdatePendingChannels(it)
                }, {
                    println("")
                })
                ?.let { disposables.add(it) }
    }

    override fun clear() {
        disposables.dispose()
    }
}
