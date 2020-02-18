package io.horizontalsystems.lightningwallet.modules.launcher

class LaunchInteractor(
//        private val accountManager: IAccountManager,
//        private val pinManager: IPinManager,
//        private val systemInfoManager: ISystemInfoManager,
//        private val keyStoreManager: IKeyStoreManager
) : LaunchModule.IInteractor {

    var delegate: LaunchModule.IInteractorDelegate? = null

    override val isPinNotSet: Boolean
        get() = false // !pinManager.isPinSet

    override val isAccountsEmpty: Boolean
        get() = false // accountManager.isAccountsEmpty

    override val isSystemLockOff: Boolean
        get() = false // systemInfoManager.isSystemLockOff

    override val isKeyInvalidated: Boolean
        get() = false // keyStoreManager.isKeyInvalidated

    override val isUserNotAuthenticated: Boolean
        get() = false // keyStoreManager.isUserNotAuthenticated

}
