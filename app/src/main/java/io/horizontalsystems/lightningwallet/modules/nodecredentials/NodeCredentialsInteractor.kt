package io.horizontalsystems.lightningwallet.modules.nodecredentials

import io.horizontalsystems.lightningkit.LndConnect
import io.horizontalsystems.lightningkit.remote.RemoteLndCredentials
import io.horizontalsystems.lightningwallet.IClipboardManager

class NodeCredentialsInteractor(
    private var clipboardManager: IClipboardManager,
    private val lndConnect: LndConnect
) : NodeCredentialsModule.IInteractor {

    override fun getPastedText(): String {
        return clipboardManager.getCopiedText()
    }

    override fun getCredentials(text: String): RemoteLndCredentials {
        return lndConnect.parse(text)
    }
}
