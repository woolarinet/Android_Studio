package com.example.assignment1125

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var state = 0
        var stopcount = 0
        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.stop)
        val pause = findViewById<Button>(R.id.pause)
        val helloText = findViewById<TextView>(R.id.time)
        var first = 0
        var temp_pause = 0

        val info = getSharedPreferences("com.example.assignment1125", Context.MODE_PRIVATE);
        val infocount:Int = info.getInt("count", 0)

        val letcount = getSharedPreferences(("com.example.assignment1125"), Context.MODE_PRIVATE)
        val edit = letcount.edit()

        var save = 0

        var temp_sec = infocount % 60
        var temp_min = infocount / 60
        var load = String.format("%02d:%02d", temp_min, temp_sec)
        helloText.setText("${load}")

        val myHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d("BkgThread", "Main thread")
                if (msg.what == 1) {
                    val sec = msg.arg1 % 60
                    val min = msg.arg1 / 60
                    val result = String.format("%02d:%02d", min, sec)
                    helloText.setText("${result}")
                    save = msg.arg1
                }
                else{
                }
            }
        }
        Thread {
            var i =0
            while(true){
                if(state == 1) {
                    if (stopcount != 0 || first == 1) {
                        i = infocount
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
                else {
                    var msg = myHandler.obtainMessage()
                    msg.what = 0
                    myHandler.sendMessage(msg)
                    edit.putInt("count", save)
                    edit.apply()
                    //Toast.makeText(this, "저장", Toast.LENGTH_SHORT).show();
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

        pause.setOnClickListener{
            if(temp_pause == 1){
                state = 1
                temp_pause = 0
            }
            else{
                state = 0
                temp_pause = 1
            }
        }
    }
}