package io.horizontalsystems.lightningwallet.modules.nodecredentials

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningwallet.QrScanActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.lightningwallet.modules.remote.ConnectActivity
import kotlinx.android.synthetic.main.activity_qr_scanner.*


class NodeCredentialsActivity : QrScanActivity() {

    private lateinit var presenter: NodeCredentialsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ViewModelProvider(this, NodeCredentialsModule.Factory()).get(NodeCredentialsPresenter::class.java)
        presenter.viewDidLoad()

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
        (presenter.view as NodeCredentialsView).startScanner.observe(this, Observer {
            openCameraWithPermission()
        })

        (presenter.view as NodeCredentialsView).showDescription.observe(this, Observer {
            errorTxt.visibility = View.INVISIBLE
            descriptionTxt.visibility = View.VISIBLE
        })

        (presenter.view as NodeCredentialsView).emptyClipboardError.observe(this, Observer {
            descriptionTxt.visibility = View.INVISIBLE
            errorTxt.setText(R.string.NodeCredentials_EmptyClipboardError)
            errorTxt.visibility = View.VISIBLE
            resetErrorWithDelay()
        })

        (presenter.view as NodeCredentialsView).invalidAddressError.observe(this, Observer {
            descriptionTxt.visibility = View.INVISIBLE
            errorTxt.setText(R.string.NodeCredentials_InvalidAddressError)
            errorTxt.visibility = View.VISIBLE
            resetErrorWithDelay()
        })

        (presenter.router as NodeCredentialsRouter).openConnectNode.observe(this, Observer { remoteLndCredentials ->
            val intent = Intent(this, ConnectActivity::class.java)
            startActivity(intent)
        })
    }

}
