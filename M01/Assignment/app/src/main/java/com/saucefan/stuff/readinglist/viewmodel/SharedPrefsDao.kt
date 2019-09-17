package com.saucefan.stuff.readinglist.viewmodel

import android.content.Context
import android.content.SharedPreferences
import com.saucefan.stuff.readinglist.model.Book

class SharedPrefsDao (context:Context) : StorageInterface{
    override fun deleteEntry(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        const val NEXT_ID="NEXTID"
        const val ID_LIST="IDLIST"
        const val INVALID_ID=-1
        const val ENTRY_ID_PREFIX ="@"
        const val READING_PREFS="SAUCE"
    }


    override fun createEntry(book: Book) {
        val ids = getListOfIds()
        if (book.id == INVALID_ID && !ids.contains(book.id.toString())) {
            val editor = sharedPrefs.edit()

            var nextID = sharedPrefs.getString(NEXT_ID, "0")
            book.id = nextID?.toInt() ?: 0
            editor.putInt(NEXT_ID, ++book.id)

            ids.add(book.id.toString())
            val newIdList = StringBuilder()
            for (id in ids) {
                newIdList.append(id).append(",")
            }
            editor.putString(NEXT_ID, newIdList.toString())
            editor.putString(ENTRY_ID_PREFIX + book.id, book.toCsvString())
            editor.apply()
        } else {
            updateEntry(book)
        }
    }



        val sharedPrefs: SharedPreferences = context.getSharedPreferences(READING_PREFS, Context.MODE_PRIVATE)


        private fun getListOfIds():ArrayList<String> {
            val idList = sharedPrefs.getString(ID_LIST,"") ?: ""
            val oldlist = idList.split(",")
            val id = ArrayList<String>(oldlist.size)
            if (idList.isNotBlank()) {
                id.addAll(oldlist)
            }
            return id
        }

        // read an existing entry
        fun readEntry (id:Int):Book? {
            val entryAsCsv = sharedPrefs.getString(ENTRY_ID_PREFIX+id,"Invalid id")
            return entryAsCsv?.let{
                Book(entryAsCsv)
            }
        }
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
    override   fun updateEntry(entry:Book){
            val editor = sharedPrefs.edit()
            editor.putString(ENTRY_ID_PREFIX + entry.id, entry.toCsvString())

        }
    }

