package io.horizontalsystems.lightningwallet.modules.settings

import io.horizontalsystems.core.ICurrencyManager
import io.horizontalsystems.core.ILanguageManager
import io.horizontalsystems.core.ISystemInfoManager
import io.horizontalsystems.core.IThemeStorage
import io.horizontalsystems.core.entities.Currency
import io.reactivex.disposables.CompositeDisposable

class MainSettingsInteractor(
        private val themeStorage: IThemeStorage,
        private val languageManager: ILanguageManager,
        private val systemInfoManager: ISystemInfoManager,
        private val currencyManager: ICurrencyManager)
    : MainSettingsModule.IMainSettingsInteractor {

    private var disposables: CompositeDisposable = CompositeDisposable()

    var delegate: MainSettingsModule.IMainSettingsInteractorDelegate? = null

    init {
        disposables.add(currencyManager.baseCurrencyUpdatedSignal.subscribe {
            delegate?.didUpdateBaseCurrency()
        })
    }

    override val currentLanguageDisplayName: String
        get() = languageManager.currentLanguageName

    override val baseCurrency: Currency
        get() = currencyManager.baseCurrency

    override var lightMode: Boolean
        get() = themeStorage.isLightModeOn
        set(value) {
            themeStorage.isLightModeOn = value
        }

    override val appVersion: String
        get() = systemInfoManager.appVersion

    override fun clear() {
        disposables.clear()
    }

}
