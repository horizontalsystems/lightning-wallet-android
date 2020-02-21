package io.horizontalsystems.lightningwallet.modules.settings.security

import android.content.Context
import android.content.Intent
import io.horizontalsystems.lightningwallet.App

object SecuritySettingsModule {

    interface ISecuritySettingsView {
        fun togglePinSet(pinSet: Boolean)
        fun setEditPinVisible(visible: Boolean)
        fun setBiometricSettingsVisible(visible: Boolean)
        fun toggleBiometricEnabled(enabled: Boolean)
    }

    interface ISecuritySettingsViewDelegate {
        fun viewDidLoad()
        fun didTapEditPin()
        fun didSwitchPinSet(enable: Boolean)
        fun didSwitchBiometricEnabled(enable: Boolean)
        fun didSetPin()
        fun didCancelSetPin()
        fun didUnlockPinToDisablePin()
        fun didCancelUnlockPinToDisablePin()
        fun onClear()
    }

    interface ISecuritySettingsInteractor {
        val biometricAuthSupported: Boolean
        val isPinSet: Boolean
        var isBiometricEnabled: Boolean

        fun disablePin()
        fun clear()
    }

    interface ISecuritySettingsRouter {
        fun showEditPin()
        fun showSetPin()
        fun showUnlockPin()
        fun restartApp()
    }

    fun start(context: Context) {
        context.startActivity(Intent(context, SecuritySettingsActivity::class.java))
    }

    fun init(view: SecuritySettingsViewModel, router: ISecuritySettingsRouter) {
        val interactor = SecuritySettingsInteractor(App.systemInfoManager, App.pinManager)
        val presenter = SecuritySettingsPresenter(router, interactor)

        view.delegate = presenter
        presenter.view = view
    }
}
