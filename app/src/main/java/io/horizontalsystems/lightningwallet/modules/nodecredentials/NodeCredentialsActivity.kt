package io.horizontalsystems.lightningwallet.modules.nodecredentials

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.MultiFormatReader
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.DecodeFormatManager
import com.google.zxing.client.android.DecodeHintManager
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.journeyapps.barcodescanner.camera.CameraSettings
import io.horizontalsystems.lightningwallet.R
import kotlinx.android.synthetic.main.activity_qr_scanner.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class NodeCredentialsActivity : AppCompatActivity() {

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

        barcodeView.decodeSingle(callback)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            barcodeView.pause()
            Log.e("QrCodeScanner", "res: ${result.text}")
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
            resultPoints.forEach {
                Log.e("QrCodeScanner", "res: $it")
            }
        }
    }

    @AfterPermissionGranted(REQUEST_CAMERA_PERMISSION)
    private fun openCameraWithPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            barcodeView.resume()
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the camera permission", REQUEST_CAMERA_PERMISSION, *perms)
        }
    }

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

    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 1
    }

}
