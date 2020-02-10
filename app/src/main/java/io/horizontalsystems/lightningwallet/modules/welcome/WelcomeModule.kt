package io.horizontalsystems.lightningwallet.modules.welcome

import android.content.Context
import android.content.Intent

object WelcomeModule {

    fun start(context: Context) {
        val intent = Intent(context, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        context.startActivity(intent)
    }

}
