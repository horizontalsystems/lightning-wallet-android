package io.horizontalsystems.lightningwallet.modules.channels

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ChannelsModule {
    fun start(context: Activity) {
        val intent = Intent(context, ChannelsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        context.startActivity(intent)
    }

    class Factory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val presenter = ChannelsPresenter()

            return presenter as T
        }
    }
}
