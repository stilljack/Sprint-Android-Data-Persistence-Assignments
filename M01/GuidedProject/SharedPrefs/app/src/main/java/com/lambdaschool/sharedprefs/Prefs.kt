package com.lambdaschool.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO: 15. A Shared Preferences helper class


class Prefs (context:Context) {
    companion object {
       private const val JOURNAL_PREFERENCES = "JOURNALPREFERENCES"
               private const val ID_LIST_KEY = "IDLISTKEY"
                private const val NEXT_ID_KEY= "NEXT ID KEY"
        private const val ENTRY_ID_PREFIX = "ENTRY ID KEY"

    }
    fun createEntry(entry:JournalEntry) {
            val id = getListOfIds()

        val ids=getListOfIds()
        if (entry.id == JournalEntry.INVALID_ID && !ids.contains(entry.id.toString())){
            val editor=sharedPrefs.edit()
            var nextID =  sharedPrefs.getInt(NEXT_ID_KEY, 0)
            entry.id = nextID
            editor.putInt(NEXT_ID_KEY, nextID++)


            ids.add(entry.id.toString())
            val newIdList = StringBuilder()
        for (id in ids) {
            newIdList.append(id).append(",")
        }
            editor.putString(NEXT_ID_KEY,newIdList.toString())

            editor.putString(ENTRY_ID_PREFIX+entry.id.toString(),entry.toCsvString())
            editor.commit()
        }else {
            updateEntry(entry)
        }


    }

val sharedPrefs:SharedPreferences = context.getSharedPreferences(JOURNAL_PREFERENCES, Context.MODE_PRIVATE)


    private fun getListOfIds():ArrayList<String> {
        val idList = sharedPrefs.getString(ID_LIST_KEY,"") ?: ""
        val oldlist = idList.split(",")
        val id = ArrayList<String>(oldlist.size)
        if (idList.isNotBlank()) {
            id.addAll(oldlist)
        }
        return id
    }

    // read an existing entry
    fun readEntry (id:Int):JournalEntry? {
        val entryAsCsv = sharedPrefs.getString(ENTRY_ID_PREFIX+id,"Invalid id")
        return entryAsCsv?.let{
            JournalEntry(entryAsCsv)
        }
    }


    // TODO: 19. This collects all known entries in Shared Preferences, with the help of the ID List
    // read all entries

    fun readAllEntries(): MutableList<JournalEntry> {
        val listOfId = getListOfIds()
        val entryList = mutableListOf<JournalEntry>()
        for (id in listOfId) {
            readEntry(id.toInt())?.let {
                entryList.add(it)
            }
        }
        return entryList
    }

    // TODO: 20. This is another way to define a SharedPreferences item
    // In Activity, can simply use: prefs.bgColor (to get and set)

    // TODO: 21. Update an entry - use CSV technique to "serialize" a Journal Entry
    // edit an existing entry

    fun updateEntry(entry:JournalEntry): JournalEntry{
        val editor = sharedPrefs.edit()
        editor.putString(ENTRY_ID_PREFIX + entry.id, entry.toCsvString())



        return entry
    }
}