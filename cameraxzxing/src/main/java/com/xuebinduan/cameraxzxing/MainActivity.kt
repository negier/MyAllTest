package com.xuebinduan.cameraxzxing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.util.Size
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import java.util.concurrent.Executors

/**
 * 相机返回的裸数据往往不是我们视图上精确看到的，往往要比我们看到的要多，如果依赖裸数据，那会导致，用户还没有看到二维码，怎么就扫成功了这样的疑问。
 * 所以解决方法，就是不用相机的裸数据，转而用我们看到的即View的图像数据喂给二维码解析库。
 */
class MainActivity : AppCompatActivity() {
    private lateinit var layoutWrap: FrameLayout
    private lateinit var viewFinder: PreviewView
    private var count: Int = 1
    private var isDecoding = false
    private var isStopDecode = false
    private val decodeHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (!isDecoding && !isStopDecode){
                isDecoding = true
                Log.e("TAGZxing", "解码中...")
                //begin decode
                val bitmap = viewFinder.bitmap
                ZxingUtils.decode(bitmap,object : ZxingUtils.DecodeCallback {
                    override fun success(result: Result?) {
                        Log.e("TAGZxing", "解码成功停止解码")
                        Log.e("TAGZxing", "result: $result")
                        isStopDecode = true
                        runOnUiThread {
                            Toast.makeText(this@MainActivity,result!!.text,Toast.LENGTH_LONG).show()
                        }
                        gotoPreview(bitmap)
                        isDecoding = false
                    }

                    override fun failure() {
                        isDecoding = false
                    }
                })
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutWrap = findViewById<FrameLayout>(R.id.layout_wrap)
        viewFinder = findViewById<PreviewView>(R.id.viewFinder)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        layoutWrap.setOnClickListener {
            gotoPreview(viewFinder.bitmap)
        }

    }

    override fun onResume() {
        super.onResume()
        isStopDecode = false
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // 图像分析器
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(Int.MAX_VALUE, Int.MAX_VALUE))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            val executor = Executors.newSingleThreadExecutor()
            imageAnalysis.setAnalyzer(executor) { imageProxy ->
                Log.e("TAG", "imageProxy:width${imageProxy.width},height${imageProxy.height}")
                Log.e("TAG", "imageProxy:${imageProxy.format}+${System.currentTimeMillis()}")
                Log.e("TAG", "imageProxy:${imageProxy.planes[0].buffer}+${System.currentTimeMillis()}")
                imageProxy.close()

/*                runOnUiThread {
                    // 这是为了验证previewView获取的Bitmap是不是都是一样的，结论是每次获取都不一样
                    Log.e("TAG","Bitmap的地址："+viewFinder.bitmap)
                }*/

                //TODO 对于QRCodeParser是不需要高频那么多次的数据传入的。需要背压设计，可以借助RxJava等，但这个貌似不太好用RxJava呀，怎么支持上去呀。
                Log.e("TAG", "次数：${count++}")

                decodeHandler.sendEmptyMessage(0)
            }

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageAnalysis, preview
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val REQUEST_CODE_PERMISSIONS = 10
        /*private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()*/
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).toTypedArray()
    }

    private fun gotoPreview(bitmap: Bitmap?) {
        PreviewImageActivity.bitmap = bitmap
        startActivity(Intent(this, PreviewImageActivity::class.java))
    }


}