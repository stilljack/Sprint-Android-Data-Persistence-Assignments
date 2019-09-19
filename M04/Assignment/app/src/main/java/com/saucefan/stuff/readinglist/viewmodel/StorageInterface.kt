package com.saucefan.stuff.readinglist.viewmodel

import com.saucefan.stuff.readinglist.model.Book


interface StorageInterface {
    fun createEntry(book: Book)
    fun readAllEntries(): MutableList<Book>
    fun updateEntry(book: Book)
    fun deleteEntry(book: Book)
}