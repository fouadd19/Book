package com.example.booklibraryapp.controllers

import androidx.viewpager2.widget.ViewPager2
import com.example.booklibraryapp.Fragment.BooksFragment
import com.example.booklibraryapp.R
import com.example.booklibraryapp.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BooksFragmentController(private val fragment : BooksFragment) {
    fun start() {
        val adapter = ViewPagerAdapter(fragment.requireActivity())
        val tabSelector : TabLayout = fragment.parentHolder.findViewById(R.id.booksTabLayout)
        val viewPager : ViewPager2 = fragment.parentHolder.findViewById(R.id.tabsViewPager)

        viewPager.adapter = adapter

        TabLayoutMediator(tabSelector, viewPager, fun(tab : TabLayout.Tab, position){
            if(position == 1){
                tab.text = "Favorites"
            }else{
                tab.text = "All Books"
            }
        }).attach()
    }
}