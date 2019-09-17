package com.saucefan.stuff.readinglist.viewmodel

import android.content.Context
import com.saucefan.stuff.readinglist.model.Book

class LocalFiles(context: Context) :StorageInterface {
    override fun createEntry(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun readAllEntries(): MutableList<Book> {
        return mutableListOf<Book>()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateEntry(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteEntry(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
