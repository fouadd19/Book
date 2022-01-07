package com.example.booklibraryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Book::class], version = 1)
abstract class BooksDB : RoomDatabase(){
    abstract fun getBookDAO() : BooksDAO

    companion object {
        fun get(context: Context) : BooksDB {
            return buildDatabase(context)
        }
        private fun buildDatabase(context: Context) : BooksDB {
            return Room.databaseBuilder(context, BooksDB::class.java, "booksDatabase")
                .allowMainThreadQueries().build()
        }
    }
}