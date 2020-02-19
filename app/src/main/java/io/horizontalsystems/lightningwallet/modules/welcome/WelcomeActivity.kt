package io.horizontalsystems.lightningwallet.modules.welcome

import android.os.Bundle
import io.horizontalsystems.lightningwallet.BaseActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.nodecredentials.NodeCredentialsActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        buttonConnect.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.captureActivity = NodeCredentialsActivity::class.java
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.setPrompt("")
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()
        }
    }

}
