package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var nonStop = true
    lateinit var textSecondsElapsed: TextView

    var backgroundThread = Thread {
        while (true) {
            if (nonStop) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run { putInt(STATE_SECONDS, secondsElapsed) }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                secondsElapsed = getInt(STATE_SECONDS)
            }
        }
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onStop() {
        super.onStop()
        nonStop = false

    }

    override fun onResume() {
        super.onResume()
        nonStop = true
    }

    companion object {
        val STATE_SECONDS = "curSec"
    }
}
