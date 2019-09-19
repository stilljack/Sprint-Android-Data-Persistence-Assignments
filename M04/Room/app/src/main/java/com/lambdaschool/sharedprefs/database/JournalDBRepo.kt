package com.lambdaschool.sharedprefs.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.lambdaschool.sharedprefs.JournalRepoInterface
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO 5: Create the Database repo and implement the methods
class JournalDBRepo(val context: Context) : JournalRepoInterface {

    override fun createEntry(entry: JournalEntry) {
        database.journalEntryDao().createEntry(entry)
    }

    override fun readAllEntries(): LiveData<List<JournalEntry>> {
        return database.journalEntryDao().readAllEntries()
    }

    override fun updateEntry(entry: JournalEntry) {
        database.journalEntryDao().updateEntry(entry)
    }

    override fun deleteEntry(entry: JournalEntry) {
        database.journalEntryDao().deleteEntry(entry)
    }

    // TODO 15: Build the Room database
    private val database/**/ by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            JournalEntryDB::class.java, "entry_database"
        ).fallbackToDestructiveMigration().build()
    }
}