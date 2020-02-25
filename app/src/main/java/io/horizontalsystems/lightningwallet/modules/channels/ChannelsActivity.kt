package io.horizontalsystems.lightningwallet.modules.channels

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.views.TopMenuItem
import kotlinx.android.synthetic.main.activity_channels.*

class ChannelsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)
        setActionBar()

        observeActions()
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
}
