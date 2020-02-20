package io.horizontalsystems.lightningwallet.modules.settings

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.core.entities.Currency
import io.horizontalsystems.lightningwallet.App

object MainSettingsModule {

    interface IMainSettingsView {
        fun setBackedUp(backedUp: Boolean)
        fun setBaseCurrency(currency: String)
        fun setLanguage(language: String)
        fun setLightMode(lightMode: Boolean)
        fun setAppVersion(appVersion: String)
    }

    interface IMainSettingsViewDelegate {
        fun viewDidLoad()
        fun didTapSecurity()
        fun didTapBaseCurrency()
        fun didTapLanguage()
        fun didSwitchLightMode(lightMode: Boolean)
        fun didTapAbout()
        fun didTapCompanyLogo()
        fun didTapReportProblem()
        fun didTapTellFriends()
        fun didTapNotifications()
    }

    interface IMainSettingsInteractor {
        // val companyWebPageLink: String
        // val appWebPageLink: String
        // val allBackedUp: Boolean
        val currentLanguageDisplayName: String
        val baseCurrency: Currency
        val appVersion: String
        var lightMode: Boolean

        fun clear()
    }

    interface IMainSettingsInteractorDelegate {
        fun didUpdateAllBackedUp(allBackedUp: Boolean)
        fun didUpdateBaseCurrency()
    }

    interface IMainSettingsRouter {
        fun showSecuritySettings()
        fun showBaseCurrencySettings()
        fun showLanguageSettings()
        fun showAbout()
        fun openLink(url: String)
        fun reloadAppInterface()
        fun showReportProblem()
        fun showShareApp(appWebPageLink: String)
        fun showNotifications()
    }

    fun start(context: Context) {
        context.startActivity(Intent(context, MainSettingsActivity::class.java))
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val view = MainSettingsView()
            val router = MainSettingsRouter()
            val interactor = MainSettingsInteractor(
                    themeStorage = App.themeStorage,
                    languageManager = App.languageManager,
                    systemInfoManager = App.systemInfoManager,
                    currencyManager = App.currencyManager
            )

            val presenter = MainSettingsPresenter(view, router, interactor).apply {
                interactor.delegate = this
            }

            return presenter as T
        }
    }
}
