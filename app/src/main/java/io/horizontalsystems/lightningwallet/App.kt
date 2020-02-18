package io.horizontalsystems.lightningwallet

import android.util.Log
import androidx.preference.PreferenceManager
import io.horizontalsystems.core.CoreApp
import io.horizontalsystems.core.IAppConfigTestMode
import io.horizontalsystems.core.ICoreApp
import io.reactivex.plugins.RxJavaPlugins
import java.util.logging.Level
import java.util.logging.Logger

class App : CoreApp() {

    companion object : ICoreApp by CoreApp {
    }

    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            //Disable logging for lower levels in Release build
            Logger.getLogger("").level = Level.SEVERE
        }

        RxJavaPlugins.setErrorHandler { e: Throwable? ->
            Log.w("RxJava ErrorHandler", e)
        }

        instance = this
        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        appConfigTestMode = object : IAppConfigTestMode {
            override val testMode: Boolean = false
        }
    }
}
