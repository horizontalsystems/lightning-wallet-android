package io.horizontalsystems.lightningwallet.storage

import io.horizontalsystems.core.IThemeStorage
import io.horizontalsystems.lightningwallet.App

class ThemeStorage : IThemeStorage {
    private val lightModeEnabled = "light_mode_enabled"

    override var isLightModeOn: Boolean
        get() = App.preferences.getBoolean(lightModeEnabled, false)
        set(enabled) {
            App.preferences.edit().putBoolean(lightModeEnabled, enabled).apply()
        }
}
