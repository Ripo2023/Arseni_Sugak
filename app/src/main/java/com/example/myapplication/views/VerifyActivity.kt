package com.example.myapplication.views

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityVerifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VerifyActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerifyBinding
    private lateinit var auth: FirebaseAuth

    companion object{
        var storedVerificationId: String? = null
        var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.back.setOnClickListener {
            finish()
        }

        binding.button.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, binding.phone.text.toString().trim())

            signInWithPhoneAuthCredential(credential)
        }

        binding.phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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

            override fun afterTextChanged(s: Editable?) {
            }

        })
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