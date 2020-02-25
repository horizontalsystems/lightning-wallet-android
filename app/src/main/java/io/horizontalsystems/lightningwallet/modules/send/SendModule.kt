package io.horizontalsystems.lightningwallet.modules.send

import android.app.Activity
import io.horizontalsystems.lightningwallet.modules.send.scanaddress.ScanAddressModule

object SendModule {

    fun start(context: Activity) {
        ScanAddressModule.start(context)
    }

}
