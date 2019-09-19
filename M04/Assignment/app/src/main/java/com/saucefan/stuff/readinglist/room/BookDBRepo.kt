package com.saucefan.stuff.readinglist.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.StorageInterface

//placeholder file for later embiggening

class BookDBRepo (val context: Context) : StorageInterface {
    override fun createEntry(book: Book) {
        database.BookDBDAO().createEntry(book)
    }

    override fun updateEntry(book: Book) {
        database.BookDBDAO().createEntry(book)
    }

    override fun deleteEntry(book: Book) {
        database.BookDBDAO().deleteEntry(book)
    }


    override fun readAllEntries(): MutableList<Book>{
        return database.BookDBDAO().readAllEntries()
    }

    // TODO 15: Build the Room database
    private val database/**/ by lazy {
        Room.databaseBuilder(
                context.applicationContext,
                BookDatabase::class.java, "book_database"
        ).fallbackToDestructiveMigration().build()
    }
}