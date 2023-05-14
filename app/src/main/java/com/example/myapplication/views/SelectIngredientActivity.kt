package com.example.myapplication.views

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySelectIngredientBinding

class SelectIngredientActivity : AppCompatActivity() {

    lateinit var binding: ActivitySelectIngredientBinding
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
    }

    private fun volumeChanged(it: View?) {
        when (it) {
            binding.oneB -> {
                changeElementFavorite(binding.oneB)
                changeElement(binding.twoB)
                changeElement(binding.threeB)
            }

            binding.twoB -> {
                changeElementFavorite(binding.twoB)
                changeElement(binding.oneB)
                changeElement(binding.threeB)
            }

            binding.threeB -> {
                changeElementFavorite(binding.threeB)
                changeElement(binding.oneB)
                changeElement(binding.twoB)
            }
        }
    }

    private fun changeElementFavorite(it: TextView) {
        it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
        it.setTextColor(resources.getColor(R.color.black))
    }

    private fun changeElement(it: TextView) {
        it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.three_gray))
        it.setTextColor(resources.getColor(R.color.two_gray))
    }
}