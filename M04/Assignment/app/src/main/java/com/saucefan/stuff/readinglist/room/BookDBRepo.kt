package com.saucefan.stuff.readinglist.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.StorageInterface

//placeholder file for later embiggening

class BookDBRepo (val context: Context) : StorageInterface {
    override fun createEntry(book: Book) {
        database.bookDBDAO().createEntry(book)
    }

    override fun updateEntry(book: Book) {
        database.bookDBDAO().updateEntry(book)
    }

    override fun deleteEntry(book: Book) {
        database.bookDBDAO().deleteEntry(book)
    }

    override fun readAllEntries(): LiveData<List<Book>> {
        return database.bookDBDAO().readAllEntries()
    }

    // TODO 15: Build the Room database
    private val database by lazy {
        Room.databaseBuilder(
                context.applicationContext,
                BookDatabase::class.java, "book_database"
        ).fallbackToDestructiveMigration().build()
    }
}