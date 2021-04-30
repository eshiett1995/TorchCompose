package com.example.torchcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.torchcompose.ui.theme.TorchComposeTheme
import com.example.torchcompose.viewmodel.TorchViewModel
import android.hardware.camera2.CameraAccessException

import android.hardware.camera2.CameraManager

import android.content.pm.PackageManager
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model: TorchViewModel by viewModels()
        model.cameraOn.observe(this, {
            val isFlashAvailable = applicationContext.packageManager
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

            if (!isFlashAvailable) {
                return@observe
            }

            val mCameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
            try {
                val mCameraId = mCameraManager.cameraIdList[0]
                mCameraManager.setTorchMode(mCameraId, it)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        })
        setContent {
            TorchComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainCompose(model = model)
                }
            }
        }
    }
}


@Composable
fun MainCompose(model : TorchViewModel) {

    TorchComposeTheme {
        val cameraOn : State<Boolean?> = model.cameraOn.observeAsState()
        Surface(color = MaterialTheme.colors.background) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

            ) {
                Button(
                    onClick = { model.switch()},
                ) {
                   Text(text = "switch ${if(cameraOn.value!!) "Off" else "On"}")
                }
            }
        }
    }
}
//testing