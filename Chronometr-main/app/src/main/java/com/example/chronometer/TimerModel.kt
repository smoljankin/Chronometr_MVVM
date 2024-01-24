package com.example.chronometer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerModel : ViewModel() {
    var isTimerRunning: Boolean = false
    var startTime: Long = 0
    var elapsedTime: Long = 0
    val storedItemsData: MutableLiveData<MutableList<String>> = MutableLiveData()
}