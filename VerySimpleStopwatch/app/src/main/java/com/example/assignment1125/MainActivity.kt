package com.example.assignment1125

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var state = 0
        var stopcount = 0
        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.stop)
        val helloText = findViewById<TextView>(R.id.time)
        var first = 0

        val myHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d("BkgThread", "Main thread")
                if (msg.what == 1) {
                    val sec = msg.arg1 % 60
                    val min = msg.arg1 / 60
                    val result = String.format("%02d:%02d", min, sec)
                    helloText.setText("${result}")
                }
            }
        }
        Thread {
            var i =0
            while(true){
                while(state == 1) {
                    if(stopcount != 0 || first == 1) {
                        i = 0
                        stopcount = 0
                        Log.d("BkgThread", "First try or Retry")
                        first += 1
                        var msg = myHandler.obtainMessage()
                        msg.what = 1
                        msg.arg1 = i
                        myHandler.sendMessage(msg)
                        Thread.sleep(1000)
                    }
                    i += 1
                    Log.d("BkgThread", "In background thread : $i")
                    var msg = myHandler.obtainMessage()
                    msg.what = 1
                    msg.arg1 = i
                    myHandler.sendMessage(msg)
                    Thread.sleep(1000)
                }
            }
        }.start()

        start.setOnClickListener{
            if (first == 0) {
                state = 1
                first = 1
            }
            else{
                state = 1
                first += 1
            }
        }

        stop.setOnClickListener{
            state = 0
            stopcount = 1
            Log.d("BkgThread", "Stop the Stopwatch")
        }
    }
}