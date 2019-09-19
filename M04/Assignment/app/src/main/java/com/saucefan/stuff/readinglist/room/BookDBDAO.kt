package com.saucefan.stuff.readinglist.room

import androidx.room.*
import com.saucefan.stuff.readinglist.model.Book

//looks like this will mostly be annotations... man if this ends up being this easy...
//i'll just be so happy!
@Dao
interface BookDBDAO {
    //
    @Insert(onConflict =  OnConflictStrategy.ABORT)
    fun createEntry(book: Book)
    @Query("SELECT * FROM Book")
    fun readAllEntries(): MutableList<Book>
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEntry(book: Book)
    @Delete
    fun deleteEntry(book: Book)

    /*
        @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createEntry(entry: JournalEntry)


    @Query("SELECT * FROM journalentry")
    fun readAllEntries(): LiveData<List<JournalEntry>>


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEntry(entry: JournalEntry)


    @Delete
    fun deleteEntry(entry: JournalEntry)
     */
}