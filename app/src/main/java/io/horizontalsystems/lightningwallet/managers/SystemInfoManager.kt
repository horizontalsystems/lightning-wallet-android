package io.horizontalsystems.lightningwallet.managers

import android.app.Activity
import android.app.KeyguardManager
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import io.horizontalsystems.core.BuildConfig
import io.horizontalsystems.core.ISystemInfoManager
import io.horizontalsystems.lightningwallet.App

class SystemInfoManager : ISystemInfoManager {

    private val biometricManager by lazy {
        BiometricManager.from(App.instance)
    }

    override val appVersion: String = BuildConfig.VERSION_NAME

    override val isSystemLockOff: Boolean
        get() {
            val keyguardManager = App.instance.getSystemService(Activity.KEYGUARD_SERVICE) as KeyguardManager
            return !keyguardManager.isDeviceSecure
        }

    override val biometricAuthSupported: Boolean
        get() = biometricManager.canAuthenticate() == BIOMETRIC_SUCCESS

    override val deviceModel: String
        get() = "${Build.MANUFACTURER} ${Build.MODEL}"

    override val osVersion: String
        get() = "Android ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})"
}
