package com.example.chronometer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    private val timerModel = TimerModel()

    fun startTimer() {
        // Логіка для початку таймера
    }

    fun stopTimer() {
        // Логіка для зупинки таймера
    }

    fun resetTimer() {
        // Логіка для скидання таймера
    }

    fun storeCurrentTime() {
        // Логіка для збереження поточного часу
    }

    fun getStoredItemsData(): LiveData<MutableList<String>> {
        return timerModel.storedItemsData
    }

    fun getElapsedTime(): Long {
        return timerModel.elapsedTime
    }
}