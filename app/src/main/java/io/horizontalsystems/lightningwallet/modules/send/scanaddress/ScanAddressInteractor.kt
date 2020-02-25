package io.horizontalsystems.lightningwallet.modules.send.scanaddress

import io.horizontalsystems.lightningwallet.IClipboardManager

class ScanAddressInteractor(private var clipboardManager: IClipboardManager) : ScanAddressModule.IInteractor {

    override fun getPastedText(): String {
        return clipboardManager.getCopiedText()
    }

}
