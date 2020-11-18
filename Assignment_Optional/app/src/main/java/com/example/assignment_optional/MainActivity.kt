package com.example.assignment_optional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.login)
        val signUp = findViewById<Button>(R.id.signUp)

        val db = Room.databaseBuilder(
                applicationContext,
                UserDB::class.java,"userdb"
        ).allowMainThreadQueries().build()

        val users = db.userDao().getAll()

        if(users.isNotEmpty()){
            Toast.makeText(this, "ID, PASSWORD를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "회원가입을 진행해 주세요.", Toast.LENGTH_SHORT).show()
        }

        btnLogin.setOnClickListener{
            val readuser = db.userDao().findByName(username.getText().toString(),
                                                    password.getText().toString())
            if(readuser != null){
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "회원가입 버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            }
        }

        signUp.setOnClickListener{
            val userd = User(0, username.getText().toString(), password.getText().toString())
            db.userDao().insertAll(userd)
            Toast.makeText(this, "회원가입이 완료되었습니다..", Toast.LENGTH_SHORT).show()
        }

    }
}