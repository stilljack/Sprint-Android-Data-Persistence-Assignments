package com.saucefan.stuff.readinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saucefan.stuff.readinglist.App.Companion.repo
import com.saucefan.stuff.readinglist.model.Book

class BookViewModel : ViewModel() {

    val entries: LiveData<List<Book>> by lazy {
        readAllEntries()
    }


    fun readAllEntries() : LiveData<List<Book>> {
        return repo!!.readAllEntries()
    }

    fun createEntry(entry: Book) {
        repo!!.createEntry(entry)
    }

    fun updateEntry(entry: Book) {
        repo!!.updateEntry(entry)
    }

    fun deleteEntry(entry:Book) {
        repo!!.deleteEntry(entry)
    }


}