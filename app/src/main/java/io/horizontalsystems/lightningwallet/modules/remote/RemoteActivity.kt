package io.horizontalsystems.lightningwallet.modules.remote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.horizontalsystems.lightningwallet.R
import kotlinx.android.synthetic.main.activity_remote.*

class RemoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)

        buttonPaste.setOnClickListener {
            val intent = Intent(this, ConnectActivity::class.java)
            startActivity(intent)
        }
    }
}
