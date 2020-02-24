package io.horizontalsystems.lightningwallet.modules.main

import androidx.lifecycle.ViewModel

class MainPresenter(private val interactor: MainModule.IInteractor)
    : ViewModel(), MainModule.IViewDelegate, MainModule.IInteractorDelegate {

    var view: MainModule.IView? = null

    override fun viewDidLoad() {
        interactor.onStart()
    }
}
