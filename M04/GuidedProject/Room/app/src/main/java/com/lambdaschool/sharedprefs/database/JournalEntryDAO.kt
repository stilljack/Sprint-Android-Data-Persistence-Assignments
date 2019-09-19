package com.lambdaschool.sharedprefs.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO 9: Let's define the DAO with methods from our repo interface
@Dao
interface JournalEntryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEntry(entry: JournalEntry)

    @Query("SELECT * FROM journalentry")
   fun readAllEntries(): LiveData<List<JournalEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateEntry(entry: JournalEntry)

    @Delete
   fun deleteEntry(entry: JournalEntry)
    // TODO 10: Insert with onConflict = REPLACE
    // TODO 11: Query for all entities
    // TODO 12: Update with onConflict = REPLACE
    // TODO 13: DELETE
}