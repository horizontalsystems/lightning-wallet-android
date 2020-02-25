package io.horizontalsystems.lightningwallet.modules.send.scanaddress

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningwallet.QrScanActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.send.inputform.InputFormModule


class ScanAddressActivity : QrScanActivity() {

    private lateinit var presenter: ScanAddressPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ViewModelProvider(this, ScanAddressModule.Factory()).get(ScanAddressPresenter::class.java)

        observeEvents()
    }

    override fun onScan(text: String) {
        presenter.onScan(text)
    }

    override fun onPaste() {
        presenter.onPaste()
    }

    override fun resetInput() {
        presenter.resetInput()
    }

    private fun observeEvents() {
        val view = presenter.view as ScanAddressView
        val router = presenter.router as ScanAddressRouter

        view.startScanner.observe(this, Observer {
            openCameraWithPermission()
        })

        view.emptyClipboardError.observe(this, Observer {
            showError(R.string.NodeCredentials_EmptyClipboardError)
        })

        view.invalidAddressError.observe(this, Observer {
            showError(R.string.NodeCredentials_InvalidAddressError)
        })

        router.openSendForm.observe(this, Observer { address ->
            InputFormModule.start(this, address)
        })
    }

}
