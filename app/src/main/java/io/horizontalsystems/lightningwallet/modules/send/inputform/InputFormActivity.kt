package io.horizontalsystems.lightningwallet.modules.send.inputform

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.lightningwallet.BaseActivity
import io.horizontalsystems.lightningwallet.R
import io.horizontalsystems.views.TopMenuItem
import kotlinx.android.synthetic.main.activity_send_form.*


class InputFormActivity : BaseActivity() {

    private lateinit var presenter: InputFormPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_form)

        val address = intent.getStringExtra(AddressKey) ?: run {
            finish()
            return
        }

        shadowlessToolbar.bind(
            getString(R.string.Send_Title, "BTC"),
            leftBtnItem = TopMenuItem(R.drawable.ic_lightning),
            rightBtnItem = TopMenuItem(text = R.string.Send_Close, onClick = { onBackPressed() })
        )

        presenter = ViewModelProvider(this, InputFormModule.Factory(address)).get(InputFormPresenter::class.java)
        presenter.viewDidLoad()

        observeEvents()
    }

    private fun observeEvents() {
        val view = presenter.view as InputFormView

        view.addressValue.observe(this, Observer {
            addressInput.setText(it)
        })

    }

    companion object {
        const val AddressKey = "send_address"
    }
}
