package com.example.booklibraryapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BooksDAO {
    @Query("select * from allBooks")
    fun getAllBooks() : List<Book>

    @Query("select exists(select isbn from allBooks where isbn = :isbn)")
    fun bookAlreadyExists(isbn : Int) : Boolean

    @Query("delete from allBooks where isbn = :isbn")
    fun deleteBook(isbn: Int)

    @Insert
    fun addBook(vararg book: Book)
}