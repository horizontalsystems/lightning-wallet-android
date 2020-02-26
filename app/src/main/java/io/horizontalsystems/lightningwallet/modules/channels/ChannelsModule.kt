package io.horizontalsystems.lightningwallet.modules.channels

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.lightningnetwork.lnd.lnrpc.Channel
import com.github.lightningnetwork.lnd.lnrpc.PendingChannelsResponse
import io.horizontalsystems.lightningwallet.App

object ChannelsModule {

    interface IView {
        fun show(viewItems: List<ChannelViewItem>)
    }

    interface IRouter {
        fun close()
    }

    interface IViewDelegate {
        fun onLoad()
        fun onNewChannel()
        fun onSelectOpen()
        fun onSelectClosed()
        fun onClose()
    }

    interface IInteractor {
        fun fetchOpenChannels()
        fun fetchPendingChannels()
        fun clear()
    }

    interface IInteractorDelegate {
        fun onUpdateOpenChannels(channels: List<Channel>)
        fun onUpdatePendingChannels(response: PendingChannelsResponse)
    }

    fun start(context: Activity) {
        val intent = Intent(context, ChannelsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        context.startActivity(intent)
    }

    class Factory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val view = ChannelsView()
            val router = ChannelsRouter()
            val interactor = ChannelsInteractor(App.lightningKitManager.currentKit)

            val presenter = ChannelsPresenter(view, router, interactor).apply {
                interactor.delegate = this
            }

            return presenter as T
        }
    }
}

class ChannelViewItem(val state: State, val remotePubKey: String, val localBalance: Int, val remoteBalance: Int) {
    enum class State {
        open,
        pendingOpen,
        pendingClosing,
        pendingForceClosing,
        waitingClose
    }
}

class ChannelsViewItemFactory {
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
