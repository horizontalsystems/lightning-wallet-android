package io.horizontalsystems.lightningwallet.modules.channels

import io.horizontalsystems.core.SingleLiveEvent

class ChannelsView : ChannelsModule.IView {

    val updateChannels = SingleLiveEvent<List<ChannelViewItem>>()

    override fun update(viewItems: List<ChannelViewItem>) {
        updateChannels.postValue(viewItems)
    }
}
