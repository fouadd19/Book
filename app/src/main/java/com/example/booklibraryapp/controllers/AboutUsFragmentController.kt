package com.example.booklibraryapp.controllers

import android.widget.TextView
import com.example.booklibraryapp.Fragment.AboutUsFragment
import com.example.booklibraryapp.R

class AboutUsFragmentController(private val fragment: AboutUsFragment) {

    fun start() {
        val description : TextView = fragment.parentHolder.findViewById(R.id.aboutUsDescription)

        description.text = fragment.getString(R.string.description)
    }
}