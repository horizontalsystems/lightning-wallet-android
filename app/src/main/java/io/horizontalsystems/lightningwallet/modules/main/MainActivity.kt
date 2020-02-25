package io.horizontalsystems.lightningwallet.modules.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningwallet.BaseActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.settings.MainSettingsModule
import io.horizontalsystems.views.TopMenuItem
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : BaseActivity() {

    private val presenter by lazy {
        ViewModelProvider(this, MainModule.Factory()).get(MainPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setActionBar()

        presenter.onLoad()

        observeEvents()
    }

    private fun setActionBar() {
        val leftBtn = TopMenuItem(R.drawable.ic_share, onClick = {
            onBackPressed()
        })

        val rightBtn = TopMenuItem(R.drawable.settings, onClick = {
            MainSettingsModule.start(this)
        })

        shadowlessToolbar.bind(null, leftBtnItem = leftBtn, rightBtnItem = rightBtn)
    }

    private fun observeEvents() {
        val view = presenter.view as MainView

        view.showBalance.observe(this, Observer {
            totalBalance.text = "$it sat"
        })

        view.showSyncingText.observe(this, Observer {
            syncingText.text = it
            syncingText.visibility = View.VISIBLE
        })

        view.hideSyncingText.observe(this, Observer {
            syncingText.visibility = View.GONE
        })
    }
}
