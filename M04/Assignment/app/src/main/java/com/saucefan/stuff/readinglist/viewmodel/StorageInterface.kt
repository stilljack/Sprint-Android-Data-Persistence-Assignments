package com.saucefan.stuff.readinglist.viewmodel

import androidx.lifecycle.LiveData
import com.saucefan.stuff.readinglist.model.Book


interface StorageInterface {
    fun createEntry(book: Book)
    fun readAllEntries(): LiveData<List<Book>>
    fun updateEntry(book: Book)
    fun deleteEntry(book: Book)
}