package io.horizontalsystems.lightningwallet

import io.horizontalsystems.core.CoreActivity
import io.horizontalsystems.pin.PinModule

abstract class BaseActivity : CoreActivity() {
    override fun onResume() {
        super.onResume()

        if (App.lockManager.isLocked) {
            PinModule.startForUnlock(this, 1)
        }
    }
}
