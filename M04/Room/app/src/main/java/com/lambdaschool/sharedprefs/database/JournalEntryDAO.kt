package com.lambdaschool.sharedprefs.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO 9: Let's define the DAO with methods from our repo interface
@Dao
interface JournalEntryDAO {

    // TODO 10: Insert with onConflict = REPLACE
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createEntry(entry: JournalEntry)

    // TODO 11: Query for all entities
    @Query("SELECT * FROM journalentry")
    fun readAllEntries(): LiveData<List<JournalEntry>> // TODO 27: Return LiveData for VM

    // TODO 12: Update with onConflict = REPLACE
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEntry(entry: JournalEntry)

    // TODO 13: DELETE
    @Delete
    fun deleteEntry(entry: JournalEntry)
}