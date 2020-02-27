package io.horizontalsystems.lightningwallet.modules.channels

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.horizontalsystems.core.setOnSingleClickListener
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.channels.ChannelViewItem.UpdateType
import io.horizontalsystems.views.helpers.AnimationHelper
import io.horizontalsystems.views.inflate
import io.horizontalsystems.views.showIf
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_channel.*

class ChannelsAdapter(private val listener: Listener) : RecyclerView.Adapter<ViewHolderChannel>() {

    interface Listener {
        fun onItemClick(item: ChannelViewItem)
        fun onClickInfo(item: ChannelViewItem)
        fun onClickManage(item: ChannelViewItem)
    }

    private var items = listOf<ChannelViewItem>()

    fun setItems(viewItems: List<ChannelViewItem>) {
        if (items.isEmpty()) {
            items = viewItems
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(ChannelViewItemDiff(items, viewItems))
            items = viewItems
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChannel {
        return ViewHolderChannel(inflate(parent, R.layout.view_holder_channel), listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolderChannel, position: Int) {
    }

    override fun onBindViewHolder(holder: ViewHolderChannel, position: Int, payloads: MutableList<Any>) {
        val item = items[position]

        if (payloads.isEmpty()) {
            holder.bind(item)
        } else {
            holder.bindUpdate(item, payloads)
        }
    }
}

class ViewHolderChannel(override val containerView: View, private val listener: ChannelsAdapter.Listener)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var channelViewItem: ChannelViewItem? = null

    init {
        containerView.isSelected = false
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

            containerView.isSelected = expanded

            buttonsWrapper.showIf(expanded)
        }
    }

    fun bindUpdate(item: ChannelViewItem, payloads: MutableList<Any>) {
        payloads.forEach {
            when (it) {
                UpdateType.EXPAND -> bindUpdateExpanded(item)
                UpdateType.UPDATE -> bind(item)
            }
        }
    }

    private fun bindUpdateExpanded(item: ChannelViewItem) {
        containerView.isSelected = item.expanded

        if (item.expanded) {
            AnimationHelper.expand(buttonsWrapper)
        } else {
            AnimationHelper.collapse(buttonsWrapper)
        }
    }
}
