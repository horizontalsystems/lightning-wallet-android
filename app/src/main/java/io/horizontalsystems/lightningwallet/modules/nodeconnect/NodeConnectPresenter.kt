package io.horizontalsystems.lightningwallet.modules.nodeconnect

import androidx.lifecycle.ViewModel
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials
import io.horizontalsystems.lightningwallet.modules.nodeconnect.NodeConnectModule.INodeConnectInteractor
import io.horizontalsystems.lightningwallet.modules.nodeconnect.NodeConnectModule.INodeConnectInteractorDelegate
import io.horizontalsystems.lightningwallet.modules.nodeconnect.NodeConnectModule.INodeConnectRouter
import io.horizontalsystems.lightningwallet.modules.nodeconnect.NodeConnectModule.INodeConnectView
import io.horizontalsystems.lightningwallet.modules.nodeconnect.NodeConnectModule.INodeConnectViewDelegate

class NodeConnectPresenter(
        val view: INodeConnectView,
        val router: INodeConnectRouter,
        private val interactor: INodeConnectInteractor,
        private val credentials: RemoteLndCredentials)
    : INodeConnectViewDelegate, INodeConnectInteractorDelegate, ViewModel() {

    //  INodeConnectViewDelegate

    override fun onLoad() {
        view.show("${credentials.host}:${credentials.port}")
    }

    override fun onClickConnect() {
        view.showConnecting()

        interactor.validate(credentials)
    }

    //  INodeConnectInteractorDelegate

    override fun onValidateCredentials() {
        view.hideConnecting()

        interactor.saveWallet(credentials)
        router.openMainModule()
    }

    override fun onFailToValidateCredentials(e: Throwable) {
        view.showError(e)
    }
}
