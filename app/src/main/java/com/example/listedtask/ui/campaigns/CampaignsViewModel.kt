package com.example.listedtask.ui.campaigns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CampaignsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is campaigns Fragment"
    }
    val text: LiveData<String> = _text
}