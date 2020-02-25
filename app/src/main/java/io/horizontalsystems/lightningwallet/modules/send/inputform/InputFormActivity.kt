package io.horizontalsystems.lightningwallet.modules.send.inputform

import android.os.Bundle
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

        shadowlessToolbar.bind(
            getString(R.string.Send_Title, "BTC"),
            leftBtnItem = TopMenuItem(R.drawable.ic_lightning),
            rightBtnItem = TopMenuItem(text = R.string.Send_Close, onClick = { onBackPressed() })
        )

        presenter = ViewModelProvider(this, InputFormModule.Factory()).get(InputFormPresenter::class.java)
//        presenter.viewDidLoad()

        observeEvents()
    }

    private fun observeEvents() {

//        (presenter.view as SendView).showDescription.observe(this, Observer {
//            errorTxt.visibility = View.INVISIBLE
//            descriptionTxt.visibility = View.VISIBLE
//        })

    }

    companion object {
        const val AddressKey = "send_address"
    }
}
