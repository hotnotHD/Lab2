package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    lateinit var sharedPref: SharedPreferences

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        sharedPref = getSharedPreferences( STATE_SECONDS, Context.MODE_PRIVATE)
    }
    override fun onStop() {
        super.onStop()
        with(sharedPref.edit()) {
            putInt(STATE_SECONDS, secondsElapsed)
            apply()
        }
    }

    override fun onStart() {
        super.onStart()
        secondsElapsed = sharedPref.getInt(STATE_SECONDS, 0)
    }

    companion object {
        val STATE_SECONDS = "curSec"
    }
}
