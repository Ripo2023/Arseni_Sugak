package com.example.myapplication.views

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySelectIngredientBinding
import com.example.myapplication.models.AboutCoffee
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SelectIngredientActivity : AppCompatActivity() {

    lateinit var volume: String

    companion object {
        lateinit var name: String
    }

    lateinit var binding: ActivitySelectIngredientBinding
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.oneB.setOnClickListener {
            volumeChanged(it)
        }

        binding.twoB.setOnClickListener {
            volumeChanged(it)
        }

        binding.threeB.setOnClickListener {
            volumeChanged(it)
        }

        binding.back.setOnClickListener {
            finish()
        }

        database = Firebase.database

        database.getReference("about_coffee/${name}")
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue<AboutCoffee>()
                    if (value != null) {
                        Glide.with(this@SelectIngredientActivity)
                            .load(value.image)
                            .into(binding.image)
                        binding.name.text = value.name
                        binding.description.text = value.desc
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun volumeChanged(it: View?) {
        when (it) {
            binding.oneB -> {
                changeElementFavorite(binding.oneB)
                changeElement(binding.twoB)
                changeElement(binding.threeB)
                volume = resources.getString(R.string._200_ml)
            }

            binding.twoB -> {
                changeElementFavorite(binding.twoB)
                changeElement(binding.oneB)
                changeElement(binding.threeB)

                volume = resources.getString(R.string._300_ml)
            }

            binding.threeB -> {
                changeElementFavorite(binding.threeB)
                changeElement(binding.oneB)
                changeElement(binding.twoB)

                volume = resources.getString(R.string._400_ml)
            }
        }
    }

    private fun changeElementFavorite(it: TextView) {
        it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
        it.setTextColor(resources.getColor(R.color.white))
    }

    private fun changeElement(it: TextView) {
        it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.three_gray))
        it.setTextColor(resources.getColor(R.color.two_gray))
    }
}