package com.example.booklibraryapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booklibraryapp.Fragment.AllBooksFragment
import com.example.booklibraryapp.Fragment.BooksFragment
import com.example.booklibraryapp.Photo
import com.example.booklibraryapp.R
import com.example.booklibraryapp.database.Book
import com.example.booklibraryapp.database.BooksDB
import com.example.booklibraryapp.database.favorites.FavoriteBook
import com.example.booklibraryapp.database.favorites.FavoriteBooksDB

class AllBooksAdapter(private val books : List<Book>, private val context: Context, private val fragment: AllBooksFragment) : RecyclerView.Adapter<AllBooksAdapter.BookHolder>() {

    class BookHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.bookImageView)
        val title : TextView = itemView.findViewById(R.id.bookTitleTextView)
        val author : TextView = itemView.findViewById(R.id.bookAuthorTextView)
        val publishedDate : TextView = itemView.findViewById(R.id.bookPublishedDateTextView)
        val price : TextView = itemView.findViewById(R.id.bookPriceTextView)
        val addToFavorites : ImageView = itemView.findViewById(R.id.addFavoritesButton)
        val deleteBook : ImageView = itemView.findViewById(R.id.bookDeleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_card_view, parent, false)
        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val database = BooksDB.get(context).getBookDAO()
        val favoritesDB = FavoriteBooksDB.get(context).getFavoritesDAO()
        val starOn = context.resources.getIdentifier("@android:drawable/btn_star_big_on", null, context.packageName)
        val starOff = context.resources.getIdentifier("@android:drawable/btn_star", null, context.packageName)
        val book = books[position]
        holder.image.setImageBitmap(loadPhoto(book.image)?.bmp)
        holder.title.text = book.title
        holder.author.text = "Author: " + book.author
        holder.publishedDate.text = "Published Date: " + book.publishedDate
        holder.price.text = "Price: " + book.price.toString() + "$"
        if(favoritesDB.favoriteAlreadyExists(book.isbn)){
            val res = context.resources.getDrawable(starOn, null)
            holder.addToFavorites.setImageDrawable(res)
        }

        holder.addToFavorites.setOnClickListener {
            if(favoritesDB.favoriteAlreadyExists(book.isbn)){
                favoritesDB.deleteFavorite(book.isbn)
                val res = context.resources.getDrawable(starOff, null)
                holder.addToFavorites.setImageDrawable(res)
            }else{
                val favorite = FavoriteBook(book.isbn, book.image, book.title, book.author, book.publishedDate, book.price)
                favoritesDB.addFavorite(favorite)
                val res = context.resources.getDrawable(starOn, null)
                holder.addToFavorites.setImageDrawable(res)
                fragment.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, BooksFragment()).commit()
            }
        }
        holder.deleteBook.setOnClickListener {
            database.deleteBook(book.isbn)
            favoritesDB.deleteFavorite(book.isbn)
            fragment.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, BooksFragment()).commit()
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    private fun loadPhoto(filename: String) : Photo? {
        val files = context.filesDir.listFiles()
        files?.filter { it.canRead() && it.isFile && it.name.startsWith(filename) && it.name.endsWith(".jpg") }?.map {
            val bytes = it.readBytes()
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            return Photo(it.name, bmp)
        }
        return null
    }
}