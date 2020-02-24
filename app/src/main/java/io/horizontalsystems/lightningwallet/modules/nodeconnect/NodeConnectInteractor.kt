package io.horizontalsystems.lightningwallet.modules.nodeconnect

import io.horizontalsystems.lightningkit.LightningKit
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials
import io.horizontalsystems.lightningwallet.managers.WalletManager
import io.horizontalsystems.lightningwallet.modules.nodeconnect.NodeConnectModule.INodeConnectInteractorDelegate
import io.reactivex.disposables.CompositeDisposable

class NodeConnectInteractor(private val walletManager: WalletManager)
    : NodeConnectModule.INodeConnectInteractor {

    var delegate: INodeConnectInteractorDelegate? = null

    private var disposables = CompositeDisposable()

    override fun validate(credentials: RemoteLndCredentials) {
        LightningKit.validateRemoteConnection(credentials)
                .subscribe({
                    delegate?.onValidateCredentials()
                }, { error ->
                    delegate?.onFailToValidateCredentials(error)
                }).let {
                    disposables.add(it)
                }
    }

    override fun saveWallet(credentials: RemoteLndCredentials) {
        walletManager.saveAndBootstrapRemoteWallet(credentials)
    }
}
