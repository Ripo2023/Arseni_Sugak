package com.example.myapplication.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.views.welcoms.FourFragment
import com.example.myapplication.views.welcoms.OneFragment
import com.example.myapplication.views.welcoms.ThreeFragment
import com.example.myapplication.views.welcoms.TwoFragment

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> {
            OneFragment()
        }
        1 -> {
            TwoFragment()
        }
        2 -> {
            ThreeFragment()
        }
        3 -> {
            FourFragment()
        }

        else -> {
            OneFragment()
        }
    }
}