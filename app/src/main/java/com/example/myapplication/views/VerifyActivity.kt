package com.example.myapplication.views

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityVerifyBinding
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Locale
import java.util.Random

class VerifyActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerifyBinding
    private lateinit var auth: FirebaseAuth
    val CHANNEL_ID = "CHANNEL"
    lateinit var database: FirebaseDatabase
    lateinit var sharedPreferences: SharedPreferences

    companion object{
        lateinit var VerificationId: String
        var phone = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("welcom", MODE_PRIVATE)

        database = Firebase.database

        Handler(Looper.getMainLooper()).postDelayed({
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Сообщение")
            alertDialog.setMessage("Код приложения: ${VerificationId}")
            alertDialog.show()
        }, 2000)

        auth = FirebaseAuth.getInstance()

        binding.back.setOnClickListener {
            finish()
        }

        binding.button.setOnClickListener {
            //Firebase Auth Phone
            /*val credential = PhoneAuthProvider.getCredential(VerificationId!!.toString(), binding.phone.text.toString().trim())
            signInWithPhoneAuthCredential(credential)*/

            val text = binding.phone.text.toString().trim()
            if(text == VerificationId){
                val uid = phone.replace("+", "")
                database.reference.child("users").child(uid).setValue(User(
                    phone = phone,
                    uid = uid
                )).addOnCompleteListener{
                    sharedPreferences.edit().putString("auth", uid).apply()
                    MainActivity.uid = uid
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            } else{
                Toast.makeText(this, "Пароли не совподают!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != "") {
                    binding.button.isEnabled = true
                    binding.button.isClickable = true
                    binding.button.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.blue))
                } else {
                    binding.button.isEnabled = false
                    binding.button.isClickable = false
                    binding.button.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.disable_button))
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }
    fun rand(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user.toString()
                    Toast.makeText(this, user, Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
    }
}