package io.horizontalsystems.lightningwallet.modules.channels

import androidx.lifecycle.ViewModel
import com.github.lightningnetwork.lnd.lnrpc.Channel
import com.github.lightningnetwork.lnd.lnrpc.PendingChannelsResponse
import com.github.lightningnetwork.lnd.lnrpc.PendingChannelsResponse.*

class ChannelsPresenter(
        val view: ChannelsModule.IView,
        val router: ChannelsModule.IRouter,
        private val interactor: ChannelsModule.IInteractor)
    : ChannelsModule.IViewDelegate, ChannelsModule.IInteractorDelegate, ViewModel() {

    private var openChannels = listOf<Channel>()
    private var pendingOpenChannels = listOf<PendingOpenChannel>()
    private var pendingClosingChannels = listOf<ClosedChannel>()
    private var pendingForceClosingChannels = listOf<ForceClosedChannel>()
    private var waitingCloseChannels = listOf<WaitingCloseChannel>()

    override fun onLoad() {
        interactor.fetchOpenChannels()
        interactor.fetchPendingChannels()
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

        val factory = ChannelsViewItemFactory()

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

        view.show(openChannelViewItems + pendingOpenChannelViewItems + pendingClosingChannelViewItems + pendingForceClosingChannelViewItems + waitingCloseChannelViewItems)

    }

    //  ViewModel

    override fun onCleared() {
        interactor.clear()
    }
}

