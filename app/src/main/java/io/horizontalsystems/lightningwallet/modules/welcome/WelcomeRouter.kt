package io.horizontalsystems.lightningwallet.modules.welcome

import io.horizontalsystems.core.SingleLiveEvent

class WelcomeRouter : WelcomeModule.IRouter {
    val navigateToRemoteConnection = SingleLiveEvent<Unit>()

    override fun navigateToRemoteConnection() {
        navigateToRemoteConnection.call()
    }
}
