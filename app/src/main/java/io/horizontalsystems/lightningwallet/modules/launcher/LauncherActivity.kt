package io.horizontalsystems.lightningwallet.modules.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.horizontalsystems.lightningwallet.modules.welcome.WelcomeModule

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WelcomeModule.start(this)
        finish()
    }
}
