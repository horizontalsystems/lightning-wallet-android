package io.horizontalsystems.lightningwallet

import android.util.Log
import androidx.preference.PreferenceManager
import io.horizontalsystems.core.CoreApp
import io.horizontalsystems.core.ICoreApp
import io.horizontalsystems.core.security.EncryptionManager
import io.horizontalsystems.core.security.KeyStoreManager
import io.horizontalsystems.lightningwallet.managers.*
import io.horizontalsystems.lightningwallet.storage.AppConfigProvider
import io.horizontalsystems.lightningwallet.storage.LocalStorage
import io.horizontalsystems.lightningwallet.storage.ThemeStorage
import io.horizontalsystems.lightningwallet.storage.WalletStorage
import io.horizontalsystems.pin.core.PinManager
import io.horizontalsystems.pin.core.SecureStorage
import io.reactivex.plugins.RxJavaPlugins
import java.util.logging.Level
import java.util.logging.Logger

class App : CoreApp() {

    companion object : ICoreApp by CoreApp {
        lateinit var localStorage: ILocalStorage
        lateinit var appConfigProvider: IAppConfigProvider
        lateinit var backgroundManager: BackgroundManager
        lateinit var walletManager: WalletManager
        lateinit var lightningKitManager: LightningKitManager

        var lastExitDate: Long = 0
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

        AppConfigProvider().apply {
            appConfigProvider = this
            appConfigTestMode = this
            languageConfigProvider = this
        }

        LocalStorage(preferences).apply {
            localStorage = this
            pinStorage = this
        }

        themeStorage = ThemeStorage()
        languageManager = LanguageManager()
        currencyManager = CurrencyManager(appConfigProvider, localStorage)

        systemInfoManager = SystemInfoManager()
        backgroundManager = BackgroundManager(this)

        KeyStoreManager("MASTER_KEY", KeyStoreCleaner(localStorage)).apply {
            keyStoreManager = this
            keyProvider = this
        }

        encryptionManager = EncryptionManager(keyProvider)
        secureStorage = SecureStorage(encryptionManager)
        pinManager = PinManager(secureStorage)

        val walletStorage = WalletStorage(preferences)

        lightningKitManager = LightningKitManager()
        walletManager = WalletManager(walletStorage, lightningKitManager)

        LockManager(pinManager).apply {
            lockManager = this
            backgroundManager.registerListener(this)
        }

        walletManager.bootstrapStoredWallet()
    }
}
