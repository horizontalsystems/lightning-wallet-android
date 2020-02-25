package io.horizontalsystems.lightningwallet.modules.welcome

import android.os.Bundle
import io.horizontalsystems.lightningwallet.BaseActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.nodecredentials.NodeCredentialsModule
import io.horizontalsystems.lightningwallet.modules.send.SendModule
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        buttonConnect.setOnClickListener {
            NodeCredentialsModule.start(this)
        }

        buttonSend.setOnClickListener {
            SendModule.start(this)
        }
    }

}
