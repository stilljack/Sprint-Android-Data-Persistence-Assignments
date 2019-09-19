package com.lambdaschool.sharedprefs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO 14: Define the Room database (abstract class with an abstract method returning the DAO

//[JournalEntry::class] vs [JournalEntry::class.java]
@Database(entities = [JournalEntry::class], version = 1,exportSchema = true)
abstract class JournalEntryDB: RoomDatabase(){
    abstract fun  entriesDao(): JournalEntryDAO
}