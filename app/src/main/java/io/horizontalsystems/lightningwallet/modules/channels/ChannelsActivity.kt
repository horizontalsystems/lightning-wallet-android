package io.horizontalsystems.lightningwallet.modules.channels

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.views.TopMenuItem
import kotlinx.android.synthetic.main.activity_channels.*

class ChannelsActivity : AppCompatActivity(), ChannelsAdapter.Listener {

    private val presenter by lazy {
        ViewModelProvider(this, ChannelsModule.Factory()).get(ChannelsPresenter::class.java)
    }

    private val channelsAdapter = ChannelsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)
        setActionBar()

        presenter.onLoad()

        channelsRecycler.adapter = channelsAdapter

        observeActions()
        observeEvents()
    }

    private fun observeActions() {
        open.isSelected = true
        open.setOnClickListener {
            selectItem(it)
        }

        closed.setOnClickListener {
            selectItem(it)
        }
    }

    private fun observeEvents() {
        val view = presenter.view as ChannelsView

        view.updateChannels.observe(this, Observer {
            channelsAdapter.setItems(it)
        })
    }

    private fun setActionBar() {
        val leftBtn = TopMenuItem(text = R.string.Main_Backup, onClick = {})

        val rightBtn = TopMenuItem(text = R.string.Button_Close, onClick = {
            onBackPressed()
        })

        shadowlessToolbar.bind(title = getString(R.string.Main_Channels), leftBtnItem = leftBtn, rightBtnItem = rightBtn)
    }

    private fun selectItem(item: View) {
        closed.isSelected = false
        open.isSelected = false
        item.isSelected = true
    }

    //  ChannelsAdapter.Listener

    override fun onItemClick(item: ChannelViewItem) {
        presenter.onSelectItem(item)
    }

    override fun onClickInfo(item: ChannelViewItem) {
    }

    override fun onClickManage(item: ChannelViewItem) {
    }
}
