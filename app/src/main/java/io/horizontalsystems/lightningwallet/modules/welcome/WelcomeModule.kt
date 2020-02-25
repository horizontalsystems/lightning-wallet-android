package io.horizontalsystems.lightningwallet.modules.welcome

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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
        val intent = Intent(context, WelcomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        context.startActivity(intent)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val interactor = WelcomeInteractor()
        val presenter = WelcomePresenter(interactor)

        interactor.delegate = presenter

        return presenter as T
    }
}

