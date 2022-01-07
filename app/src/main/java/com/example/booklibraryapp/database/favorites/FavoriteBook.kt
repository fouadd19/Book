package com.example.booklibraryapp.database.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteBook (
    @PrimaryKey
    @ColumnInfo
    val isbn : Int,

    @ColumnInfo
    val image : String,

    @ColumnInfo
    val title : String,

    @ColumnInfo
    val author : String,

    @ColumnInfo
    val publishedDate : String,

    @ColumnInfo
    val price : Int

    )