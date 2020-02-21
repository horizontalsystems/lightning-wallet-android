package io.horizontalsystems.lightningwallet.modules.welcome

import android.content.Intent
import android.os.Bundle
import io.horizontalsystems.lightningwallet.BaseActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.remote.RemoteActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        buttonConnect.setOnClickListener {
            val intent = Intent(this, RemoteActivity::class.java)
            startActivity(intent)
        }
    }

}
