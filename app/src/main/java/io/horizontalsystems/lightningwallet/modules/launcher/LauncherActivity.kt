package io.horizontalsystems.lightningwallet.modules.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningwallet.modules.home.HomeModule
import io.horizontalsystems.lightningwallet.modules.welcome.WelcomeModule

class LauncherActivity : AppCompatActivity() {

    private lateinit var presenter: LaunchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ViewModelProvider(this, LaunchModule.Factory()).get(LaunchPresenter::class.java)
        presenter.viewDidLoad()

        observeEvents()
    }

    private fun observeEvents() {
        val router = presenter.router as LaunchRouter

        router.openWelcomeModule.observe(this, Observer {
            WelcomeModule.start(this)
            finish()
        })

        router.openMainModule.observe(this, Observer {
            HomeModule.start(this)
            finish()
        })

        router.openUnlockModule.observe(this, Observer {
            // LockScreenModule.startForUnlock(this, REQUEST_CODE_UNLOCK_PIN)
        })

        router.openNoSystemLockModule.observe(this, Observer {
            // KeyStoreModule.startForNoSystemLock(this)
        })

        router.openKeyInvalidatedModule.observe(this, Observer {
            // KeyStoreModule.startForInvalidKey(this)
        })

        router.openUserAuthenticationModule.observe(this, Observer {
            // KeyStoreModule.startForUserAuthentication(this)
        })

        router.closeApplication.observe(this, Observer {
            finishAffinity()
        })
    }
}
