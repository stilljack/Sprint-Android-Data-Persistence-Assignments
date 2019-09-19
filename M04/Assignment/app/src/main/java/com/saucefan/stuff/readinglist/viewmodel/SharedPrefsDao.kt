package com.saucefan.stuff.readinglist.viewmodel

import android.content.Context
import android.content.SharedPreferences
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.getNewID
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.idCount
import org.json.JSONException

class SharedPrefsDao (context:Context) : StorageInterface{


    companion object {
        const val ID_LIST_KEY="IDLIST"
        const val ENTRY_ID_PREFIX ="liquid"
        const val READING_PREFS="SAUCE"
    }
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(READING_PREFS, Context.MODE_PRIVATE)
    fun readEntry (id:Int):Book? {
        val entryAsCsv:String = sharedPrefs.getString(ENTRY_ID_PREFIX+id,"Invalid id") as String
        var book = Book(entryAsCsv)
        //what could possibly go wrong?
        idCount=book.id
        return book
    }
    private fun getListOfIds():ArrayList<String> {
        val idList = sharedPrefs.getString(ID_LIST_KEY,"") ?: ""
        val oldlist = idList.split(",")
        val ids = ArrayList<String>()
        if (idList.isNotBlank()) {
            ids.addAll(oldlist)
        }
        return ids
    }

    override fun createEntry(book: Book) {
        val ids = getListOfIds()
        if (ids.contains(book.id.toString())){
            updateEntry(book)
        }else {
            val editor = sharedPrefs.edit()
            var nextID = book.id
            ids.add(book.id.toString())
            var newIdList = StringBuilder()
            for (id in ids) {
                if(id!="" && id.toInt() >= 0) {
                    // i wonder if that even protects me from anything... whatever it lives here now
                    newIdList.append(id).append(",")
                }
            }

            //should equal ids + "," like "0,1,2,etc"
            editor.putString(ID_LIST_KEY, newIdList.toString())
            editor.putString(ENTRY_ID_PREFIX + book.id, book.toCsvString())
            editor.commit()
        }
    }






        // read an existing entry

      override  fun readAllEntries(): MutableList<Book> {
            val listOfId = getListOfIds()
            val entryList = mutableListOf<Book>()
            for (id in listOfId) {
                if (id.isNotBlank()) {
                    readEntry(id.toInt())?.let {
                        entryList.add(it)
                    }
                }
            }
            return entryList
        }


    override fun updateEntry(book:Book){
            val editor = sharedPrefs.edit()
            editor.putString(ENTRY_ID_PREFIX + book.id, book.toCsvString())
        editor.commit()
        }
    override fun deleteEntry(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    }

