package io.horizontalsystems.lightningwallet.modules.send.inputform

import androidx.lifecycle.ViewModel

class InputFormPresenter(
    val view: InputFormModule.IView,
    val router: InputFormModule.IRouter,
    private val interactor: InputFormModule.IInteractor,
    private val address: String
) : ViewModel(), InputFormModule.IViewDelegate {

    override fun viewDidLoad() {
        view.setAddress(address)
    }

}
