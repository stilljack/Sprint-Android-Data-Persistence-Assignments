package com.saucefan.stuff.readinglist.viewmodel

import android.content.Context
import android.os.Environment
import com.saucefan.stuff.readinglist.model.Book
import java.io.File

class LocalFiles(var context: Context) :StorageInterface {
    //check permissions
    val isExternalStorageWriteable:Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return state == Environment.MEDIA_MOUNTED
        }
    //define a directory to save to
    val storageDirectory:File
        get() {
            if (isExternalStorageWriteable) {
                val directory = context.filesDir
                return if (!directory.exists() && !directory.mkdirs()) {
                    context.cacheDir
                } else {
                    directory
                }
            } else {
                return context.cacheDir
            }
    }
    //get list of files that match our purpose (are.json files within stoarageDirectory)
    //does this need to be a var? does this need to get updated?
    val filelist:ArrayList<String>
        get(){
            val fileNames = arrayListOf<String>()
            val dir = storageDirectory
            val list =dir.list()
            if (list !=null) {
                for (name in list) {
                    if(name.contains(".json")) {
                        fileNames.add(name)
                    }
                }
            }
            return fileNames
        }



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
