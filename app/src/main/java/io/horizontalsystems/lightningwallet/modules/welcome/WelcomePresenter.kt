package io.horizontalsystems.lightningwallet.modules.welcome

import androidx.lifecycle.ViewModel

class WelcomePresenter(private val interactor: WelcomeModule.IInteractor) :
    WelcomeModule.IViewDelegate,
        WelcomeModule.IInteractorDelegate, ViewModel() {

    val router = WelcomeRouter()

    override fun connect() {
        router.navigateToRemoteConnection()
    }
}
