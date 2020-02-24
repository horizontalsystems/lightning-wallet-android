package io.horizontalsystems.lightningwallet.modules.remote

import android.content.Intent
import android.os.Bundle
import io.horizontalsystems.lightningwallet.BaseActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.nodeconnect.NodeConnectActivity
import kotlinx.android.synthetic.main.activity_remote.*

class RemoteActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)

        title = null
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonPaste.setOnClickListener {
            val intent = Intent(this, NodeConnectActivity::class.java)
            startActivity(intent)
        }
    }
}
