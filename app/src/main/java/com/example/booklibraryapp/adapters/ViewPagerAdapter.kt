package com.example.booklibraryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.booklibraryapp.Fragment.AllBooksFragment
import com.example.booklibraryapp.Fragment.FavoriteBooksFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 1){
            return FavoriteBooksFragment()
        }
        return AllBooksFragment()
    }
}