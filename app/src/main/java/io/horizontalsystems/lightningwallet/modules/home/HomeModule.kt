package io.horizontalsystems.lightningwallet.modules.home

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object HomeModule {

    interface IView

    interface IViewDelegate {
        fun viewDidLoad()
    }

    interface IInteractor {
        fun onStart()
    }

    interface IInteractorDelegate

    interface IRouter

    fun start(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        context.startActivity(intent)
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val interactor = HomeInteractor()
            val presenter = HomePresenter(interactor)

            interactor.delegate = presenter

            return presenter as T
        }
    }
}
