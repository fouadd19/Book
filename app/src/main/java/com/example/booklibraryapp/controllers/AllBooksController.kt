package com.example.booklibraryapp.controllers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booklibraryapp.Fragment.AllBooksFragment
import com.example.booklibraryapp.R
import com.example.booklibraryapp.adapters.AllBooksAdapter
import com.example.booklibraryapp.database.Book
import com.example.booklibraryapp.database.BooksDB

class AllBooksController(private val fragment : AllBooksFragment) {

    private lateinit var recyclerView : RecyclerView

    fun start() {
        val database =  BooksDB.get(fragment.requireActivity()).getBookDAO()
        val books = database.getAllBooks()

        recyclerView = fragment.parentHolder.findViewById(R.id.allBooksRecyclerView)

        showData(books)
    }
    private fun showData(books : List<Book>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = AllBooksAdapter(books, this.context, fragment)
        }
    }
}