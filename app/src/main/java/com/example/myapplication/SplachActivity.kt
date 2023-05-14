package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postAtTime

class SplachActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)
        sharedPreferences = getSharedPreferences("welcom", MODE_PRIVATE)

        Thread {

        }.start()

        if (sharedPreferences.getString("welcome", "") != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            sharedPreferences.edit().putString("welcome", "yew").apply()
        }
    }
}