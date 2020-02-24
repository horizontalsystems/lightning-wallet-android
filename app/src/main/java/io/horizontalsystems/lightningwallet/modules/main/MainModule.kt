package io.horizontalsystems.lightningwallet.modules.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object MainModule {

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
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        context.startActivity(intent)
    }

    fun startAsNewTask(context: Activity) {
        start(context)
        context.overridePendingTransition(0, 0)
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val interactor = MainInteractor()
            val presenter = MainPresenter(interactor)

            interactor.delegate = presenter

            return presenter as T
        }
    }
}
