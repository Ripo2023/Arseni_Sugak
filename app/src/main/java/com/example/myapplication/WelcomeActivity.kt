package com.example.myapplication

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.myapplication.adapters.PageAdapter
import com.example.myapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.viewPager) {
            //this = ViewPager(this.context)
            adapter = PageAdapter(supportFragmentManager)
            currentItem = 0
        }
    }

    override fun onBackPressed() {
        when (binding.viewPager.currentItem) {
            0 -> {
                finishAffinity()
            }

            1 -> {
                binding.viewPager.currentItem = 0
            }

            2 -> {
                binding.viewPager.currentItem = 1
            }

            3 -> {
                binding.viewPager.currentItem = 2
            }
        }
    }
}