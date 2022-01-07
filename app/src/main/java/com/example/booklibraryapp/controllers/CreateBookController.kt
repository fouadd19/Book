package com.example.booklibraryapp.controllers

import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibraryapp.Fragment.CreateBookFragment
import com.example.booklibraryapp.R
import com.example.booklibraryapp.activities.MainActivity
import com.example.booklibraryapp.database.Book
import com.example.booklibraryapp.database.BooksDB
import java.io.IOException

class CreateBookController (private val fragment : CreateBookFragment) {
    private val image : ImageView = fragment.parentHolder.findViewById(R.id.createBookImage)
    private val isbn : EditText = fragment.parentHolder.findViewById(R.id.isbnBookEditText)
    private val title : EditText = fragment.parentHolder.findViewById(R.id.titleBookEditText)
    private val author : EditText = fragment.parentHolder.findViewById(R.id.authorBookEditText)
    private val date : EditText = fragment.parentHolder.findViewById(R.id.publishedDateBookEditText)
    private val price : EditText = fragment.parentHolder.findViewById(R.id.priceBookEditText)
    private val createButton : Button = fragment.parentHolder.findViewById(R.id.createButton)
    private val database = BooksDB.get(fragment.requireActivity()).getBookDAO()

    private var selectedImage : Bitmap? = null

    private val imageLoader = fragment.registerForActivityResult(ActivityResultContracts.GetContent()) {
        selectedImage = MediaStore.Images.Media.getBitmap(fragment.requireActivity().contentResolver, it)
        image.setImageBitmap(selectedImage)
    }

    fun start() {
        image.setOnClickListener {
            imageLoader.launch("image/*")
        }

        createButton.setOnClickListener {
            val isbnCorrect: Boolean
            val titleCorrect: Boolean
            val authorCorrect: Boolean
            val dateCorrect: Boolean
            val priceCorrect: Boolean

            when {
                isbn.text.isEmpty() -> {
                    isbn.error = fragment.getString(R.string.emptyField)
                    isbnCorrect = false
                }
                database.bookAlreadyExists(Integer.parseInt(isbn.text.toString())) -> {
                    isbn.error = "Book Already Exists"
                    isbnCorrect = false
                }
                else -> {
                    isbnCorrect = true
                }
            }
            if (title.text.isEmpty()) {
                title.error = fragment.getString(R.string.emptyField)
                titleCorrect = false
            } else {
                titleCorrect = true
            }
            if (author.text.isEmpty()) {
                author.error = fragment.getString(R.string.emptyField)
                authorCorrect = false
            } else {
                authorCorrect = true
            }
            if (date.text.isEmpty()) {
                date.error = fragment.getString(R.string.emptyField)
                dateCorrect = false
            } else {
                dateCorrect = true
            }
            if (price.text.isEmpty()) {
                price.error = fragment.getString(R.string.emptyField)
                priceCorrect = false
            } else {
                priceCorrect = true
            }

            if (isbnCorrect && titleCorrect && authorCorrect && dateCorrect && priceCorrect) {
                if (selectedImage != null) {
                    savePhoto(isbn.text.toString(), selectedImage!!)
                }
                val dir = isbn.text.toString() + ".jpg"
                val book = Book(
                    Integer.parseInt(isbn.text.toString()),
                    dir,
                    title.text.toString(),
                    author.text.toString(),
                    date.text.toString(),
                    Integer.parseInt(price.text.toString())
                )
                database.addBook(book)
                Toast.makeText(fragment.requireActivity(), fragment.getString(R.string.bookCreated), Toast.LENGTH_LONG).show()
                fragment.startActivity(Intent(fragment.requireActivity(), MainActivity::class.java))
            } else {
                Toast.makeText(fragment.requireActivity(), fragment.getString(R.string.somethingWentWrong), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    private fun savePhoto(filename : String, bmp : Bitmap) : Boolean {
        return try {
            fragment.requireActivity().openFileOutput("$filename.jpg", AppCompatActivity.MODE_PRIVATE).use { stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)){
                    throw IOException("Couldn't save bmp")
                }
            }
            true
        }catch(e : IOException){
            e.printStackTrace()
            false
        }
    }
}