package io.horizontalsystems.lightningwallet.managers

import io.horizontalsystems.core.IKeyStoreCleaner
import io.horizontalsystems.lightningwallet.ILocalStorage

class KeyStoreCleaner(private val localStorage: ILocalStorage) : IKeyStoreCleaner {

    override var encryptedSampleText: String?
        get() = localStorage.encryptedSampleText
        set(value) {
            localStorage.encryptedSampleText = value
        }

    override fun cleanApp() {
        localStorage.clear()
    }
}
