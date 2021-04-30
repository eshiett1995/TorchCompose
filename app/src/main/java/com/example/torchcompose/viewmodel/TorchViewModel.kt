package com.example.torchcompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TorchViewModel : ViewModel() {
    var cameraOn : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun switch(){
        cameraOn.value = !cameraOn.value!!
    }
}