package com.example.booklibraryapp.database.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesDAO {

    @Query("select * from favorites")
    fun getAllFavorites() : List<FavoriteBook>

    @Query("select exists(select isbn from favorites where isbn = :isbn)")
    fun favoriteAlreadyExists(isbn : Int) : Boolean

    @Query("delete from favorites where isbn = :isbn")
    fun deleteFavorite(isbn: Int)

    @Insert
    fun addFavorite(vararg favorite : FavoriteBook)
}