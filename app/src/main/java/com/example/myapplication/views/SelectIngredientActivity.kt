package com.example.myapplication.views

import android.content.SharedPreferences
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.adapters.GridListAdapter
import com.example.myapplication.databinding.ActivitySelectIngredientBinding
import com.example.myapplication.models.AboutCoffee
import com.example.myapplication.models.CoffeeModel
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
        lateinit var naming: String
    }

    lateinit var binding: ActivitySelectIngredientBinding
    lateinit var database: FirebaseDatabase
    lateinit var espanol: String

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("welcom", MODE_PRIVATE)

        val database = Firebase.database

        //get list ingridients
        val uid = sharedPreferences.getString("auth", "").toString()
        database.getReference("product_list/$uid/${naming}")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    when (dataSnapshot.child("volume").value.toString()) {
                        "200 ml" -> {
                            volumeChanged(binding.oneB)
                        }

                        "300 ml" -> {
                            volumeChanged(binding.twoB)
                        }

                        "400 ml" -> {
                            volumeChanged(binding.threeB)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        //get list data about coffee
        database.getReference("about_coffee/${naming.lowercase()}")
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue<AboutCoffee>()
                    if (value != null) {
                        Glide.with(this@SelectIngredientActivity)
                            .load(value.image)
                            .into(binding.image)
                        binding.name.text = value.name
                        binding.description.text = value.desc
                        espanol = value.espanol.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        //change ml to coffee
        binding.oneB.setOnClickListener {
            val uid = sharedPreferences.getString("auth", "").toString()
            database.reference.child("product_list")
                .child(uid).child(naming).child("volume").setValue("200 ml")
            volumeChanged(it)
        }

        binding.twoB.setOnClickListener {
            val uid = sharedPreferences.getString("auth", "").toString()
            database.reference.child("product_list")
                .child(uid).child(naming).child("volume").setValue("300 ml")
            volumeChanged(it)
        }

        binding.threeB.setOnClickListener {
            val uid = sharedPreferences.getString("auth", "").toString()
            database.reference.child("product_list")
                .child(uid).child(naming).child("volume").setValue("400 ml")
            volumeChanged(it)
        }

        binding.back.setOnClickListener {
            finish()
        }

        binding.info.setOnClickListener {
            Toast.makeText(this, espanol, Toast.LENGTH_SHORT).show()
        }
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

    //change color buttons
    private fun changeElementFavorite(it: TextView) {
        it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
        it.setTextColor(resources.getColor(R.color.white))
    }

    private fun changeElement(it: TextView) {
        it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.three_gray))
        it.setTextColor(resources.getColor(R.color.two_gray))
    }
}