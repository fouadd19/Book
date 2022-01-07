package com.example.booklibraryapp.database.favorites

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteBook::class], version = 1)
abstract class FavoriteBooksDB : RoomDatabase(){
    abstract fun getFavoritesDAO() : FavoritesDAO

    companion object {
        fun get(context: Context) : FavoriteBooksDB {
            return buildDatabase(context)
        }
        private fun buildDatabase(context: Context) : FavoriteBooksDB {
            return Room.databaseBuilder(context, FavoriteBooksDB::class.java, "FavoriteBooksDatabase")
                .allowMainThreadQueries().build()
        }
    }
}