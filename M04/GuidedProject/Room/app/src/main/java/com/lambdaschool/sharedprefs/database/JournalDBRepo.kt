package com.lambdaschool.sharedprefs.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.lambdaschool.sharedprefs.JournalRepoInterface
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO 5: Create the Database repo and implement the methods

class JournalDBRepo(context: Context) : JournalRepoInterface {
   private val contxt:Context=context.applicationContext



    override fun createEntry(entry: JournalEntry) {
/*databaseif (hasInternet) {
    retrofit.createEntry(entry) {
        onresponse{
            database.entriesDao().createEntry(response.body as JournalEntry)
        }
    }

    repo needs to decide were to look for data
}*/
        database.entriesDao().createEntry(entry)

    }

    override fun readAllEntries(): LiveData<List<JournalEntry>> {

        return database.entriesDao().readAllEntries()
    }

    override fun updateEntry(entry: JournalEntry) {
        database.entriesDao().updateEntry(entry)
    }

    override fun deleteEntry(entry: JournalEntry) {
        database.entriesDao().deleteEntry(entry)

    }
    // TODO 15: Build the Room database
//Room.database(context, database class, name)
    private val database by lazy {
        Room.databaseBuilder(
            contxt,
            JournalEntryDB::class.java,
            "enatry_database"
        ).addMigrations().fallbackToDestructiveMigration().build()

        /// here be retrofit demons in the future
    }
}

