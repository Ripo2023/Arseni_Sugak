package com.example.myapplication.views

import android.app.Notification
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignBinding
import com.example.myapplication.databinding.ActivityWelcomeBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Random
import java.util.concurrent.TimeUnit

class SignActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("welcom", MODE_PRIVATE)

        binding.button.isEnabled = false
        binding.button.isClickable = false

        binding.agree.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && binding.phone.text.isNotBlank()){
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


        //listner for textview
        binding.phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != "" && binding.agree.isChecked) {
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


        binding.button.setOnClickListener {

            //Firebase Auth Phone
            /*val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(binding.phone.text.toString().trim())
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Toast.makeText(this@SignActivity, R.string.error_text, Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        VerifyActivity.VerificationId = verificationId
                        startActivity(Intent(this@SignActivity, VerifyActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)*/

            val database = Firebase.database
            val uid = VerifyActivity.phone.replace("+", null.toString())
            /*val tt = Firebase.database.reference.child("users").orderByChild("uid").equalTo(uid).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                    if (snapshot != null) {
                        sharedPreferences.edit().putString("auth", "yew").apply()
                        startActivity(Intent(this@SignActivity, MainActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })*/

            //create verify code for reg
            val code = rand(0, 1000)
            VerifyActivity.VerificationId = code.toString()
            VerifyActivity.phone = binding.phone.text.toString()
            startActivity(Intent(this@SignActivity, VerifyActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
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
                }
            }
    }
}