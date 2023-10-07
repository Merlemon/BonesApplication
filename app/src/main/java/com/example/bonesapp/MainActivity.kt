package com.example.bonesapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.TextureView
import android.widget.Button

class MainActivity : AppCompatActivity() {

    //код индусика, вероятно придется интегрировать его на следующую страницу и поменять структуру камеры
    lateinit var handler: Handler
    lateinit var handlerThread: HandlerThread
    lateinit var cameraManager: CameraManager
    lateinit var textureView: TextureView
    lateinit var cameraCaptureSession: CameraCaptureSession
    lateinit var cameraDevice: CameraDevice
    lateinit var captureRequest: CaptureRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.starter)

        startButton.setOnClickListener {
            val intent = Intent(this, TipBeforeCameraPage::class.java)
            startActivity(intent)
        }

        get_permissions()

        textureView = findViewById(R.id.textureView)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handlerThread = HandlerThread("video thread")
        handlerThread.start()
        handler = Handler((handlerThread).looper)


        textureView.surfaceTextureListener= object: TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
//                open_camera()
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
                TODO("Not yet implemented")
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                TODO("Not yet implemented")
            }
        }


    }


//    @SuppressLint("MissingPermission")
  //  fun open_camera{
    //    cameraManager.openCamera(cameraManager.cameraIdList[0], object:CameraDevice.StateCallback(){
      //      override fun onOpened(p0: CameraDevice) {
        //        cameraDevice = p0
          //  }
//
  //          override fun onDisconnected(p0: CameraDevice) {
//
  //          }
//
  //          override fun onError(p0: CameraDevice, p1: Int) {
//
  //          }
    //    }, handler)
    //}


    fun get_permissions()
    {
        var permissionsLst = mutableListOf<String>()

        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionsLst.add(android.Manifest.permission.CAMERA)
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionsLst.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionsLst.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionsLst.size > 0){
            requestPermissions(permissionsLst.toTypedArray(), 101)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it != PackageManager.PERMISSION_GRANTED){
                get_permissions()
            }
        }
    }
}