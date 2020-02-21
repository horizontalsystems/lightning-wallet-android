package io.horizontalsystems.lightningwallet.modules.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningwallet.modules.home.HomeModule
import io.horizontalsystems.lightningwallet.modules.welcome.WelcomeModule
import io.horizontalsystems.pin.PinModule

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
            PinModule.startForUnlock(this, REQUEST_CODE_UNLOCK_PIN)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_UNLOCK_PIN) {
            when (resultCode) {
                PinModule.RESULT_OK -> presenter.didUnlock()
                PinModule.RESULT_CANCELLED -> presenter.didCancelUnlock()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_UNLOCK_PIN = 1
    }
}
