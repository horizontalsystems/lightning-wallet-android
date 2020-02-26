package io.horizontalsystems.lightningwallet.modules.channels

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.horizontalsystems.core.setOnSingleClickListener
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.views.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_channel.*

class ChannelsAdapter(private val listener: Listener) : RecyclerView.Adapter<ViewHolderChannel>() {

    interface Listener {
        fun onItemClick(item: ChannelViewItem)
        fun onClickInfo(item: ChannelViewItem)
        fun onClickManage(item: ChannelViewItem)
    }

    private var viewItems = listOf<ChannelViewItem>()

    fun setItems(items: List<ChannelViewItem>) {
        viewItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChannel {
        return ViewHolderChannel(inflate(parent, R.layout.view_holder_channel), listener)
    }

    override fun getItemCount(): Int {
        return viewItems.size
    }

    override fun onBindViewHolder(holder: ViewHolderChannel, position: Int) {
        holder.bind(viewItems[position])
    }
}

class ViewHolderChannel(override val containerView: View, private val listener: ChannelsAdapter.Listener)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var channelViewItem: ChannelViewItem? = null

    init {
        containerView.setOnClickListener {
            channelViewItem?.let {
                listener.onItemClick(it)
            }
        }

        buttonManage.setOnSingleClickListener {
            channelViewItem?.let {
                listener.onClickManage(it)
            }
        }

        buttonInfo.setOnSingleClickListener {
            channelViewItem?.let {
                listener.onClickInfo(it)
            }
        }
    }

    fun bind(item: ChannelViewItem) {
        channelViewItem = item

        item.apply {
            iconCoin.bind("BTC")

            channelId.text = remotePubKey
            channelState.text = state.name

            val total = localBalance + remoteBalance

            balanceCoin.text = "$total"
            balanceFiat.text = "$total"

            canSentAmount.text = "$localBalance"
            canReceiveAmount.text = "$remoteBalance"
        }
    }
}
