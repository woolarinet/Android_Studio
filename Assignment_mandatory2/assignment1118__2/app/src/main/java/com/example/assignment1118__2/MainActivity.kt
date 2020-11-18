package com.example.assignment1118__2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Firstname = findViewById<EditText>(R.id.Firstname)
        val Lastname = findViewById<EditText>(R.id.Lastname)
        val btnInsert = findViewById<Button>(R.id.Insert)


        val db = Room.databaseBuilder(
                applicationContext,
                UserDB::class.java,"userdb"
        ).allowMainThreadQueries().build()

        val users = db.userDao().getAll()

        if(users.isNotEmpty()) {
            Log.d("USERDB","something in db")

            var readuser = db.userDao().findByName("jungwoo", "Park")

            if(readuser != null){
                Firstname.setText(readuser.firstName.toString())
                Lastname.setText(readuser.lastName.toString())
                Toast.makeText(this, "회원정보를 불러왔습니다.", Toast.LENGTH_SHORT).show()
                Log.d("USERDB","Loaded user's information in db")
            }
            else{
                Toast.makeText(this, "찾는 정보가 없습니다.입력하세yo", Toast.LENGTH_SHORT).show()
                Log.d("USERDB","NO user's information in db")
            }
        }
        else {
            Log.d("USERDB","Nothing in db")
            Toast.makeText(this, "회원정보가 없습니다.입력하세yo", Toast.LENGTH_SHORT).show()
        }

        btnInsert.setOnClickListener{
            val userd = User(1, Firstname.getText().toString(), Lastname.getText().toString())
            db.userDao().insertAll(userd)
            Toast.makeText(this, "회원정보가 저장 되었습니다.", Toast.LENGTH_SHORT).show()
            Log.d("USERDB","Saved user's information in db")
        }
    }
}