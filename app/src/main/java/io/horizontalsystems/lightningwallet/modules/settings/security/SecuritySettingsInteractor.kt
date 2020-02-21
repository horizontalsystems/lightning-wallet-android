package io.horizontalsystems.lightningwallet.modules.settings.security

import io.horizontalsystems.core.IPinManager
import io.horizontalsystems.core.ISystemInfoManager
import io.reactivex.disposables.CompositeDisposable

class SecuritySettingsInteractor(
        private val systemInfoManager: ISystemInfoManager,
        private val pinManager: IPinManager)
    : SecuritySettingsModule.ISecuritySettingsInteractor {

    private var disposables: CompositeDisposable = CompositeDisposable()

    override val biometricAuthSupported: Boolean
        get() = systemInfoManager.biometricAuthSupported

    override var isBiometricEnabled: Boolean
        get() = pinManager.isFingerprintEnabled
        set(value) {
            pinManager.isFingerprintEnabled = value
        }

    override val isPinSet: Boolean
        get() = pinManager.isPinSet

    override fun disablePin() {
        pinManager.clear()
    }

    override fun clear() {
        disposables.clear()
    }
}
