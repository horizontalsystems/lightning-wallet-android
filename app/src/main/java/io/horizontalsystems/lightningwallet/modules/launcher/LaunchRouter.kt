package io.horizontalsystems.lightningwallet.modules.launcher

import io.horizontalsystems.core.SingleLiveEvent

class LaunchRouter : LaunchModule.IRouter {

    val openWelcomeModule = SingleLiveEvent<Void>()
    val openMainModule = SingleLiveEvent<Void>()
    val openUnlockModule = SingleLiveEvent<Void>()
    val openNoSystemLockModule = SingleLiveEvent<Void>()
    val openKeyInvalidatedModule = SingleLiveEvent<Void>()
    val openUserAuthenticationModule = SingleLiveEvent<Void>()
    val closeApplication = SingleLiveEvent<Void>()

    // IRouter

    override fun openWelcomeModule() {
        openWelcomeModule.call()
    }

    override fun openMainModule() {
        openMainModule.call()
    }

    override fun openUnlockModule() {
        openUnlockModule.call()
    }

    override fun openNoSystemLockModule() {
        openNoSystemLockModule.call()
    }

    override fun openKeyInvalidatedModule() {
        openKeyInvalidatedModule.call()
    }

    override fun openUserAuthenticationModule() {
        openUserAuthenticationModule.call()
    }

    override fun closeApplication() {
        closeApplication.call()
    }
}
