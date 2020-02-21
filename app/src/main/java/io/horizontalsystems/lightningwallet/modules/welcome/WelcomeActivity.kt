package io.horizontalsystems.lightningwallet.modules.welcome

import android.os.Bundle
import io.horizontalsystems.lightningwallet.BaseActivity
import androidx.appcompat.app.AppCompatActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.nodecredentials.NodeCredentialsModule
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        buttonConnect.setOnClickListener {
            NodeCredentialsModule.start(this)
        }
    }

}
