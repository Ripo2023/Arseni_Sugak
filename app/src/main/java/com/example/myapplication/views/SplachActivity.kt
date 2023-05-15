package com.example.myapplication.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class SplachActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)
        sharedPreferences = getSharedPreferences("welcom", MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPreferences.getString("welcome", "") != "") {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }, 1500)
    }
}