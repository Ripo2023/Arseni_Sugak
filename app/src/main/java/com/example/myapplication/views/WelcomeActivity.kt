package com.example.myapplication.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.myapplication.R
import com.example.myapplication.adapters.PageAdapter
import com.example.myapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("welcom", MODE_PRIVATE)

        with(binding.viewPager) {
            adapter = PageAdapter(supportFragmentManager)
            overScrollMode = View.OVER_SCROLL_NEVER
            currentItem = 0

            val next = resources.getString(R.string.next)
            val finish = resources.getString(R.string.finish)

            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    when (binding.viewPager.currentItem) {
                        0 -> {
                            binding.button.text = next
                        }

                        1 -> {
                            binding.button.text = next
                        }

                        2 -> {
                            binding.button.text = next
                        }

                        3 -> {
                            binding.button.text = finish
                        }
                    }
                }

                override fun onPageSelected(position: Int) {
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

            })
        }

        binding.button.setOnClickListener {
            when (binding.viewPager.currentItem) {
                0 -> {
                    binding.viewPager.currentItem = 1
                }

                1 -> {
                    binding.viewPager.currentItem = 2
                }

                2 -> {
                    binding.viewPager.currentItem = 3
                }

                3 -> {
                    sharedPreferences.edit().putString("welcome", "yew").apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
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