package io.horizontalsystems.lightningwallet.modules.channels

import io.horizontalsystems.core.SingleLiveEvent

class ChannelsView : ChannelsModule.IView {

    val showChannels = SingleLiveEvent<List<ChannelViewItem>>()

    override fun show(viewItems: List<ChannelViewItem>) {
        showChannels.postValue(viewItems)
    }
}
