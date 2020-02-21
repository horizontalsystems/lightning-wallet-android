package io.horizontalsystems.lightningwallet.modules.home

import android.os.Bundle
import io.horizontalsystems.lightningwallet.BaseActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.settings.MainSettingsModule
import io.horizontalsystems.views.TopMenuItem
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setActionBar()
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
}
