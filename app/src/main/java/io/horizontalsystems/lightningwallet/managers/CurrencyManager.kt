package io.horizontalsystems.lightningwallet.managers

import io.horizontalsystems.core.ICurrencyManager
import io.horizontalsystems.core.entities.Currency
import io.horizontalsystems.lightningwallet.IAppConfigProvider
import io.horizontalsystems.lightningwallet.ILocalStorage
import io.reactivex.subjects.PublishSubject

class CurrencyManager(appConfigProvider: IAppConfigProvider, private val localStorage: ILocalStorage) : ICurrencyManager {

    override var baseCurrency: Currency
        get() {
            return currencies.find { it.code == localStorage.baseCurrencyCode } ?: currencies.first()
        }
        set(value) {
            localStorage.baseCurrencyCode = value.code
            baseCurrencyUpdatedSignal.onNext(Unit)
        }

    override val currencies = appConfigProvider.currencies
    override val baseCurrencyUpdatedSignal = PublishSubject.create<Unit>()
}
