package io.horizontalsystems.lightningwallet.storage

import io.horizontalsystems.core.IAppConfigTestMode
import io.horizontalsystems.core.ILanguageConfigProvider
import io.horizontalsystems.core.entities.Currency
import io.horizontalsystems.lightningwallet.IAppConfigProvider

class AppConfigProvider : IAppConfigProvider, ILanguageConfigProvider, IAppConfigTestMode {

    //  IAppConfigProvider

    override val currencies: List<Currency> = listOf(
            Currency(code = "USD", symbol = "\u0024"),
            Currency(code = "EUR", symbol = "\u20AC"),
            Currency(code = "GBP", symbol = "\u00A3"),
            Currency(code = "JPY", symbol = "\u00A5")
    )

    //  ILanguageConfigProvider

    override val localizations: List<String>
        get() {
            val coinsString = "de,en,es,fa,fr,ko,ru,tr,zh" // App.instance.getString(R.string.localizations)
            return coinsString.split(",")
        }

    //  IAppConfigTestMode

    override val testMode: Boolean = false

}
