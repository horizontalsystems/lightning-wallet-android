package io.horizontalsystems.lightningwallet.modules.launcher

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningwallet.App

object LaunchModule {

    interface IView

    interface IViewDelegate {
        fun viewDidLoad()
        fun didUnlock()
        fun didCancelUnlock()
    }

    interface IInteractor {
        val isPinNotSet: Boolean
        val isAccountsEmpty: Boolean
        val isSystemLockOff: Boolean
        val isKeyInvalidated: Boolean
        val isUserNotAuthenticated: Boolean
    }

    interface IInteractorDelegate

    interface IRouter {
        fun openWelcomeModule()
        fun openMainModule()
        fun openUnlockModule()
        fun closeApplication()
        fun openNoSystemLockModule()
        fun openKeyInvalidatedModule()
        fun openUserAuthenticationModule()
    }

    fun start(context: Context) {
        val intent = Intent(context, LauncherActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        context.startActivity(intent)
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val router = LaunchRouter()
            val interactor = LaunchInteractor(App.walletManager, App.pinManager, App.systemInfoManager, App.keyStoreManager)
            val presenter = LaunchPresenter(router, interactor)

            interactor.delegate = presenter

            return presenter as T
        }
    }
}
