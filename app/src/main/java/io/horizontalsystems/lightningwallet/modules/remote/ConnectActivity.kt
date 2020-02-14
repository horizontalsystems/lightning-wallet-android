package io.horizontalsystems.lightningwallet.modules.remote

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import io.horizontalsystems.lightningwallet.BaseActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.home.HomeActivity
import kotlinx.android.synthetic.main.activity_connect.*

class ConnectActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        title = getString(R.string.Remote_Connect)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        addressValue.text = "192.168.0.55:10009"

        buttonConnect.setOnClickListener {
            buttonConnect.isEnabled = false
            progress.visibility = View.VISIBLE

            Handler().postDelayed({
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }, 1000)
        }

    }
}
