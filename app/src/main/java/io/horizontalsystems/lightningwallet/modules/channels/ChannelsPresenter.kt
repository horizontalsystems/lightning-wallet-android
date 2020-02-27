package io.horizontalsystems.lightningwallet.modules.channels

import androidx.lifecycle.ViewModel
import com.github.lightningnetwork.lnd.lnrpc.Channel
import com.github.lightningnetwork.lnd.lnrpc.PendingChannelsResponse
import com.github.lightningnetwork.lnd.lnrpc.PendingChannelsResponse.*
import io.horizontalsystems.lightningwallet.modules.channels.ChannelViewItem.UpdateType

class ChannelsPresenter(
        val view: ChannelsModule.IView,
        val router: ChannelsModule.IRouter,
        private val interactor: ChannelsModule.IInteractor,
        private val factory: ChannelViewItemFactory)
    : ChannelsModule.IViewDelegate, ChannelsModule.IInteractorDelegate, ViewModel() {

    private var viewItems = mutableListOf<ChannelViewItem>()

    private var openChannels = listOf<Channel>()
    private var pendingOpenChannels = listOf<PendingOpenChannel>()
    private var pendingClosingChannels = listOf<ClosedChannel>()
    private var pendingForceClosingChannels = listOf<ForceClosedChannel>()
    private var waitingCloseChannels = listOf<WaitingCloseChannel>()

    override fun onLoad() {
        interactor.fetchOpenChannels()
        interactor.fetchPendingChannels()
    }

    override fun onSelectItem(item: ChannelViewItem) {
        for (viewItem in viewItems) {
            if (viewItem.remotePubKey == item.remotePubKey) {
                viewItem.updateType = UpdateType.EXPAND
                viewItem.expanded = !viewItem.expanded

                continue
            }

            if (viewItem.expanded) {
                viewItem.apply {
                    expanded = false
                    updateType = UpdateType.EXPAND
                }
            }
        }

        view.update(viewItems.map { it.copy() })
    }

    override fun onNewChannel() {
    }

    override fun onSelectOpen() {
    }

    override fun onSelectClosed() {
    }

    override fun onClose() {
        router.close()
    }

    //  IInteractorDelegate

    override fun onUpdateOpenChannels(channels: List<Channel>) {
        openChannels = channels

        syncView()
    }

    override fun onUpdatePendingChannels(response: PendingChannelsResponse) {
        pendingOpenChannels = response.pendingOpenChannelsList
        pendingClosingChannels = response.pendingClosingChannelsList
        pendingForceClosingChannels = response.pendingForceClosingChannelsList
        waitingCloseChannels = response.waitingCloseChannelsList

        syncView()
    }

    private fun syncView() {

        val openChannelViewItems = openChannels.map {
            factory.viewItem(it)
        }
        val pendingOpenChannelViewItems = pendingOpenChannels.map {
            factory.viewItem(ChannelViewItem.State.pendingOpen, it.channel)
        }
        val pendingClosingChannelViewItems = pendingClosingChannels.map {
            factory.viewItem(ChannelViewItem.State.pendingClosing, it.channel)
        }
        val pendingForceClosingChannelViewItems = pendingForceClosingChannels.map {
            factory.viewItem(ChannelViewItem.State.pendingForceClosing, it.channel)
        }
        val waitingCloseChannelViewItems = waitingCloseChannels.map {
            factory.viewItem(ChannelViewItem.State.waitingClose, it.channel)
        }

        viewItems = (openChannelViewItems + pendingOpenChannelViewItems + pendingClosingChannelViewItems + pendingForceClosingChannelViewItems + waitingCloseChannelViewItems).toMutableList()

        view.update(viewItems)
    }

    //  ViewModel

    override fun onCleared() {
        interactor.clear()
    }
}

