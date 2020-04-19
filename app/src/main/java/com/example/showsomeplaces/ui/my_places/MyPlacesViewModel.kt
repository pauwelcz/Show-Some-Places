package com.example.showsomeplaces.ui.my_places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyPlacesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my places Fragment"
    }
    val text: LiveData<String> = _text
}