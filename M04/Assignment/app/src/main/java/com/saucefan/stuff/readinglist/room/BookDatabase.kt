package com.saucefan.stuff.readinglist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saucefan.stuff.readinglist.model.Book

//placeholder file for later embiggening

@Database(entities = [Book::class], version = 1, exportSchema = true)
abstract class BookDatabase : RoomDatabase() {
    abstract fun BookDBDAO(): BookDBDAO
}