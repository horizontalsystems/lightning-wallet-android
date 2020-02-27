package io.horizontalsystems.lightningwallet.modules.channels

import androidx.recyclerview.widget.DiffUtil
import com.github.lightningnetwork.lnd.lnrpc.Channel
import com.github.lightningnetwork.lnd.lnrpc.PendingChannelsResponse

data class ChannelViewItem(
        val state: State,
        val remotePubKey: String,
        val localBalance: Int,
        val remoteBalance: Int,
        var expanded: Boolean = false,
        var updateType: UpdateType? = UpdateType.UPDATE) {

    enum class State {
        open,
        pendingOpen,
        pendingClosing,
        pendingForceClosing,
        waitingClose
    }

    enum class UpdateType {
        UPDATE,
        EXPAND
    }
}

class ChannelViewItemFactory {

    fun viewItem(channel: Channel): ChannelViewItem {
        return ChannelViewItem(
                state = ChannelViewItem.State.open,
                remotePubKey = channel.remotePubkey,
                localBalance = channel.localBalance.toInt(),
                remoteBalance = channel.remoteBalance.toInt()
        )
    }

    fun viewItem(state: ChannelViewItem.State, pendingChannel: PendingChannelsResponse.PendingChannel): ChannelViewItem {
        return ChannelViewItem(
                state = state,
                remotePubKey = pendingChannel.remoteNodePub,
                localBalance = pendingChannel.localBalance.toInt(),
                remoteBalance = pendingChannel.remoteBalance.toInt()
        )
    }
}

class ChannelViewItemDiff(private val oldItems: List<ChannelViewItem>, private val newItems: List<ChannelViewItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].remotePubKey == newItems[newItemPosition].remotePubKey
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newItems[newItemPosition].updateType
    }
}
