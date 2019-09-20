package com.lambdaschool.sharedprefs

import androidx.lifecycle.LiveData
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO 4: We want the database repo to implement this interface

interface JournalRepoInterface {
    fun createEntry(entry: JournalEntry)
    // TODO 28: LiveData in the interface
    fun readAllEntries(): LiveData<List<JournalEntry>>
    fun updateEntry(entry: JournalEntry)
    fun deleteEntry(entry: JournalEntry)
}