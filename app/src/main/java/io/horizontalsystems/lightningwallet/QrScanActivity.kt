package io.horizontalsystems.lightningwallet

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.android.DecodeFormatManager
import com.google.zxing.client.android.DecodeHintManager
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.journeyapps.barcodescanner.camera.CameraSettings
import kotlinx.android.synthetic.main.activity_qr_scanner.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

abstract class QrScanActivity: BaseActivity() {

    private val callback = BarcodeCallback {
        barcodeView.pause()
        onScan(it.text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        appBarLayout.setPadding(0, getStatusBarHeight(), 0, 0)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = 0

        initializeFromIntent(intent)

        buttonPaste.setOnClickListener {
            onPaste()
        }

        barcodeView.decodeContinuous(callback)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        openCameraWithPermission()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    abstract fun onScan(text: String)

    abstract fun onPaste()

    @AfterPermissionGranted(REQUEST_CAMERA_PERMISSION)
    protected fun openCameraWithPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            barcodeView.resume()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.NodeCredentials_PleaseGrantCameraPermission),
                REQUEST_CAMERA_PERMISSION, *perms)
        }
    }

    protected fun resetErrorWithDelay() {
        //reset after 3 seconds
        Handler().postDelayed({
            resetInput()
        }, 3 * 1000)
    }

    abstract fun resetInput()

    private fun initializeFromIntent(intent: Intent) {
        // Scan the formats the intent requested, and return the result to the calling activity.
        val decodeFormats = DecodeFormatManager.parseDecodeFormats(intent)
        val decodeHints = DecodeHintManager.parseDecodeHints(intent)
        val settings = CameraSettings()
        if (intent.hasExtra(Intents.Scan.CAMERA_ID)) {
            val cameraId =
                intent.getIntExtra(Intents.Scan.CAMERA_ID, -1)
            if (cameraId >= 0) {
                settings.requestedCameraId = cameraId
            }
        }

        // Check what type of scan. Default: normal scan
        val scanType = intent.getIntExtra(Intents.Scan.SCAN_TYPE, 0)
        val characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET)
        val reader = MultiFormatReader()
        reader.setHints(decodeHints)
        barcodeView.cameraSettings = settings
        barcodeView.decoderFactory = DefaultDecoderFactory(
            decodeFormats,
            decodeHints,
            characterSet,
            scanType
        )
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 1
    }

}
