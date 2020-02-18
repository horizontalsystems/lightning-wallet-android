package io.horizontalsystems.lightningwallet.modules.launcher

import androidx.lifecycle.ViewModel

class LaunchPresenter(val router: LaunchModule.IRouter, private val interactor: LaunchModule.IInteractor)
    : ViewModel(), LaunchModule.IViewDelegate, LaunchModule.IInteractorDelegate {

    var view: LaunchModule.IView? = null

    //  IViewDelegate methods

    override fun viewDidLoad() {
        router.openWelcomeModule()

        // when {
        //     interactor.isSystemLockOff -> router.openNoSystemLockModule()
        //     interactor.isKeyInvalidated -> router.openKeyInvalidatedModule()
        //     interactor.isUserNotAuthenticated -> router.openUserAuthenticationModule()
        //     interactor.isAccountsEmpty -> router.openWelcomeModule()
        //     interactor.isPinNotSet -> router.openMainModule()
        //     else -> router.openUnlockModule()
        // }
    }

    override fun didUnlock() {
        router.openMainModule()
    }

    override fun didCancelUnlock() {
        router.closeApplication()
    }

}
