package com.example.chronometer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val timerViewModel: TimerViewModel by viewModels()

    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var storeButton: Button
    private lateinit var storedItems: ListView

    private lateinit var adapter: ArrayAdapter<String>

    private val handler = Handler(Looper.getMainLooper())
    private val updateTask = object : Runnable {
        override fun run() {
            val elapsedTime = timerViewModel.getElapsedTime()
            updateChronoMeter(elapsedTime)
            handler.postDelayed(this, 10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText = findViewById(R.id.timerText)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        resetButton = findViewById(R.id.resetButton)
        storeButton = findViewById(R.id.storeButton)
        storedItems = findViewById(R.id.storedItems)

        stopButton.isVisible = false
        resetButton.isEnabled = false
        storeButton.isEnabled = false

        adapter = object : ArrayAdapter<String>(
            this,
            R.layout.text_and_button,
            R.id.item_text,
            mutableListOf()
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val closeButton: Button = view.findViewById(R.id.close_button)

                // Handle button click
                closeButton.setOnClickListener {
                    val storedItems = timerViewModel.getStoredItemsData().value
                    storedItems?.removeAt(position)
                    adapter.notifyDataSetChanged()
                }

                return view
            }
        }

        storedItems.adapter = adapter

        timerViewModel.getStoredItemsData().observe(this, Observer {
            adapter.clear()
            adapter.addAll(it)
            adapter.notifyDataSetChanged()
        })

        startButton.setOnClickListener {
            timerViewModel.startTimer()
        }

        stopButton.setOnClickListener {
            timerViewModel.stopTimer()
        }

        resetButton.setOnClickListener {
            timerViewModel.resetTimer()
        }

        storeButton.setOnClickListener {
            timerViewModel.storeCurrentTime()
        }
    }

    private fun updateChronoMeter(elapsedTime: Long) {
        timerText.text = formatElapsedTime(elapsedTime)
    }

    private fun formatElapsedTime(elapsedTime: Long): String {
        val seconds = elapsedTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val millis = (elapsedTime % 1000) / 10

        return String.format("%02d:%02d:%02d.%02d", hours, minutes % 60, seconds % 60, millis)
    }
}
