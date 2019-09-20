package com.lambdaschool.sharedprefs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO 14: Define the Room database (abstract class with an abstract method returning the DAO
@Database(entities = [JournalEntry::class], version = 2, exportSchema = false)
abstract class JournalEntryDB : RoomDatabase() {
    abstract fun journalEntryDao(): JournalEntryDAO
}
