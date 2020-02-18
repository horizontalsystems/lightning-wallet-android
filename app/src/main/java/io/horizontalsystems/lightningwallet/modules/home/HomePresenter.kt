package io.horizontalsystems.lightningwallet.modules.home

import androidx.lifecycle.ViewModel

class HomePresenter(private val interactor: HomeModule.IInteractor)
    : ViewModel(), HomeModule.IViewDelegate, HomeModule.IInteractorDelegate {

    var view: HomeModule.IView? = null

    override fun viewDidLoad() {
        interactor.onStart()
    }
}
