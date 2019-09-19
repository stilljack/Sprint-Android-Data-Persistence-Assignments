package com.saucefan.stuff.readinglist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saucefan.stuff.readinglist.model.Book


@Database(entities = [Book::class], version = 2, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDBDAO(): BookDBDAO
}