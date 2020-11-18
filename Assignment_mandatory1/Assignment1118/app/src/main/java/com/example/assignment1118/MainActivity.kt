package com.example.assignment1118

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.login)

        val info = getSharedPreferences("com.example.assignment1118", Context.MODE_PRIVATE);
        val infouser:String? = info.getString("username", "unknown")
        val infopassword:String? = info.getString("password", "****")
        val infoID = info.getInt("id", -1)

        val letlogin = getSharedPreferences("com.example.assignment1118", Context.MODE_PRIVATE);
        val edit = letlogin.edit()

        if(infoID == -1){
            Toast.makeText(this, "로그인 정보가 없습니다.입력하세yo", Toast.LENGTH_SHORT).show()
            Log.d("USER","Nothing in Information")
        }

        else if(infoID != -1){
            username.setText(infouser)
            password.setText(infopassword)
            Toast.makeText(this, "로그인 정보를 불러왔습니다.", Toast.LENGTH_SHORT).show();
            Log.d("USER","Got something in Information")
        }

        btnLogin.setOnClickListener{
            edit.putString("username", username.getText().toString())
            edit.putString("password", password.getText().toString())
            edit.putInt("id", 1)
            edit.apply()
            Toast.makeText(this, "로그인 정보가 저장 되었습니다.", Toast.LENGTH_SHORT).show();
            Log.d("USER","Saving a Information")
        }
    }
}
