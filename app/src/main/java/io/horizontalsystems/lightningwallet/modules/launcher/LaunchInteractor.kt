package io.horizontalsystems.lightningwallet.modules.launcher

import io.horizontalsystems.core.IKeyStoreManager
import io.horizontalsystems.core.IPinManager
import io.horizontalsystems.core.ISystemInfoManager
import io.horizontalsystems.lightningwallet.managers.WalletManager

class LaunchInteractor(
        private val walletManager: WalletManager,
        private val pinManager: IPinManager,
        private val systemInfoManager: ISystemInfoManager,
        private val keyStoreManager: IKeyStoreManager)
    : LaunchModule.IInteractor {

    var delegate: LaunchModule.IInteractorDelegate? = null

    override val isPinNotSet: Boolean
        get() = !pinManager.isPinSet

    override val isAccountsEmpty: Boolean
        get() = !walletManager.hasStoredWallet

    override val isSystemLockOff: Boolean
        get() = systemInfoManager.isSystemLockOff

    override val isKeyInvalidated: Boolean
        get() = keyStoreManager.isKeyInvalidated

    override val isUserNotAuthenticated: Boolean
        get() = keyStoreManager.isUserNotAuthenticated

}
