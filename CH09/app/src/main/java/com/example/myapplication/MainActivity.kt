package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var progressRabbit = 0
    private var progressTurtle = 0
    private var btnstart: Button? = null
    private var sbrabbit: SeekBar? = null
    private var sbturtle: SeekBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnstart = findViewById(R.id.btn_start)
        sbrabbit = findViewById(R.id.sb_rabbit)
        sbturtle = findViewById(R.id.sb_turtle)
        btnstart?.run {
            setOnClickListener(View.OnClickListener {
                setEnabled(false)
                progressRabbit = 0
                progressTurtle = 0
                sbrabbit.run { setProgress(0) }
                sbturtle.run { setProgress(0) }
                runRabbit()
                runTurtle()
            })
        }
    }

    private val handler = Handler(
        Looper.myLooper()!!
    ) { msg ->
        if (msg.what == 1) sbrabbit!!.progress =
            progressRabbit else if (msg.what == 2) sbturtle!!.progress =
            progressTurtle
        if (progressRabbit >= 100 && progressTurtle < 100) {
            Toast.makeText(this@MainActivity, "兔子勝利", Toast.LENGTH_SHORT).show()
            btnstart!!.isEnabled = true
        } else if (progressTurtle >= 100 && progressRabbit < 100) {
            Toast.makeText(this@MainActivity, "烏龜勝利", Toast.LENGTH_SHORT).show()
            btnstart!!.isEnabled = true
        }
        false
    }

    private fun runRabbit() {
        Thread {
            val sleepProbability = booleanArrayOf(true, true, false)
            while (progressRabbit <= 100 && progressTurtle < 100) {
                try {
                    Thread.sleep(100)
                    if (sleepProbability[(Math.random() * 3).toInt()]) Thread.sleep(
                        300
                    )
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressRabbit += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private fun runTurtle() {
        Thread {
            while (progressTurtle <= 100 && progressRabbit < 100) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressTurtle += 1
                val msg = Message()
                msg.what = 2
                handler.sendMessage(msg)
            }
        }.start()
    }
}