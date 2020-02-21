package io.horizontalsystems.lightningwallet

import io.horizontalsystems.core.entities.AppVersion
import io.horizontalsystems.core.entities.Currency

interface IAppConfigProvider {
    val currencies: List<Currency>
}

interface ILocalStorage {
    var iUnderstand: Boolean
    var baseCurrencyCode: String?

    var appVersions: List<AppVersion>
    var isAlertNotificationOn: Boolean
    var encryptedSampleText: String?

    fun clear()
}

interface IClipboardManager {
    fun copyText(text: String)
    fun getCopiedText(): String
}
