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
import com.example.booklibraryapp.Fragment.BooksFragment
import com.example.booklibraryapp.Fragment.FavoriteBooksFragment
import com.example.booklibraryapp.Photo
import com.example.booklibraryapp.R
import com.example.booklibraryapp.database.BooksDB
import com.example.booklibraryapp.database.favorites.FavoriteBook
import com.example.booklibraryapp.database.favorites.FavoriteBooksDB

class FavoriteBooksAdapter(private val favorites : List<FavoriteBook>, private val context: Context, private val fragment: FavoriteBooksFragment) : RecyclerView.Adapter<FavoriteBooksAdapter.FavoriteHolder>() {

    class FavoriteHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.bookImageView)
        val title : TextView = itemView.findViewById(R.id.bookTitleTextView)
        val author : TextView = itemView.findViewById(R.id.bookAuthorTextView)
        val publishedDate : TextView = itemView.findViewById(R.id.bookPublishedDateTextView)
        val price : TextView = itemView.findViewById(R.id.bookPriceTextView)
        val addToFavorites : ImageView = itemView.findViewById(R.id.addFavoritesButton)
        val deleteBookButton : ImageView = itemView.findViewById(R.id.bookDeleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_card_view, parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val database = FavoriteBooksDB.get(context).getFavoritesDAO()
        val allBooksDB = BooksDB.get(context).getBookDAO()
        val favorite = favorites[position]
        val starOn = context.resources.getIdentifier("@android:drawable/btn_star_big_on", null, context.packageName)
        val res = context.resources.getDrawable(starOn, null)
        holder.image.setImageBitmap(loadPhoto(favorite.image)?.bmp)
        holder.title.text = favorite.title
        holder.author.text = "Author: " + favorite.author
        holder.publishedDate.text = "Published Date: " + favorite.publishedDate
        holder.price.text = "Price: " + favorite.price.toString() + "$"
        holder.addToFavorites.setImageDrawable(res)
        holder.addToFavorites.setOnClickListener {
            database.deleteFavorite(favorite.isbn)
            fragment.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, BooksFragment()).commit()
        }
        holder.deleteBookButton.setOnClickListener {
            allBooksDB.deleteBook(favorite.isbn)
            database.deleteFavorite(favorite.isbn)
            fragment.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, BooksFragment()).commit()
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
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