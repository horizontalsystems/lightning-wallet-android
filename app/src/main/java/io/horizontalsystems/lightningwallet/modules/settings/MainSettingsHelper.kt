package io.horizontalsystems.lightningwallet.modules.settings

import io.horizontalsystems.core.entities.Currency

class MainSettingsHelper {

    fun displayName(baseCurrency: Currency): String {
        return baseCurrency.code
    }

}
