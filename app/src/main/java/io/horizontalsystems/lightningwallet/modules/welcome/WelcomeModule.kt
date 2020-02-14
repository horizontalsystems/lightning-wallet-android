package io.horizontalsystems.lightningwallet.modules.welcome

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.core.SingleLiveEvent

object WelcomeModule : ViewModelProvider.Factory {

    interface IView
    interface IViewDelegate {
        fun connect()
    }

    interface IInteractor
    interface IInteractorDelegate
    interface IRouter {
        fun navigateToRemoteConnection()
    }

    fun start(context: Context) {
        val intent = Intent(context, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        context.startActivity(intent)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val interactor = WelcomeInteractor()
        val presenter = WelcomePresenter(interactor)

        interactor.delegate = presenter

        return presenter as T
    }
}

class WelcomeRouter : WelcomeModule.IRouter {
    val navigateToRemoteConnection = SingleLiveEvent<Unit>()

    override fun navigateToRemoteConnection() {
        navigateToRemoteConnection.call()
    }
}

class WelcomePresenter(private val interactor: WelcomeModule.IInteractor) :
    WelcomeModule.IViewDelegate,
    WelcomeModule.IInteractorDelegate, ViewModel() {

    val router = WelcomeRouter()

    override fun connect() {
        router.navigateToRemoteConnection()
    }
}

class WelcomeInteractor : WelcomeModule.IInteractor {
    lateinit var delegate: WelcomeModule.IInteractorDelegate
}
