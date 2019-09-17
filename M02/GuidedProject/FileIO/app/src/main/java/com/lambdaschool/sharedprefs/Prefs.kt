package com.lambdaschool.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import com.lambdaschool.sharedprefs.model.JournalEntry

// TODO: 15. A Shared Preferences helper class
class Prefs(context: Context) {
    companion object {
        private const val JOURNAL_PREFERENCES = "JournalPreferences"

        // TODO: 16. KEYS for Shared Preferences can be defined as Constants here
        private const val ID_LIST_KEY = "id_list"
        private const val ENTRY_ITEM_KEY_PREFIX = "entry_"
        private const val NEXT_ID_KEY = "next_id"

        private const val BACKGROUND_COLOR = "background_color"  // and example
    }

    val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(JOURNAL_PREFERENCES, Context.MODE_PRIVATE)

    // TODO: 17. Each Journal Entry will be its own entry in shared preferences
    // create a new entry
    fun createEntry(entry: JournalEntry) {
        // read list of entry ids
        val ids = getListOfIds()

        if (entry.id == JournalEntry.INVALID_ID && !ids.contains(entry.id.toString())) {
            // new entry
            val editor = sharedPrefs.edit()

            var nextId = sharedPrefs.getInt(NEXT_ID_KEY, 0)
            entry.id = nextId
            // store updated next id
            editor.putInt(NEXT_ID_KEY, ++nextId)

            // add id to list of ids

            ids.add(Integer.toString(entry.id))
            // store updated id list
            val newIdList = StringBuilder()
            for (id in ids) {
                newIdList.append(id).append(",")
            }

            editor.putString(ID_LIST_KEY, newIdList.toString())

            // store new entry
            editor.putString(ENTRY_ITEM_KEY_PREFIX + entry.id, entry.toCsvString())
            editor.apply()
        } else {
            updateEntry(entry)
        }
    }

    // TODO: 18. IDs are all stored as a CSV list in one SharedPreferences entry
    private fun getListOfIds(): java.util.ArrayList<String> {
        val idList = sharedPrefs.getString(ID_LIST_KEY, "")
        val oldList = idList!!.split(",")

        val ids = ArrayList<String>(oldList.size)
        if (idList.isNotBlank()) {
            ids.addAll(oldList)
        }
        return ids
    }

    // read an existing entry
    private fun readEntry(id: Int): JournalEntry? {
        val entryCsv = sharedPrefs.getString(ENTRY_ITEM_KEY_PREFIX + id, "invalid")!!
        return if (entryCsv != "invalid") {
            JournalEntry(entryCsv)
        } else {
            null
        }
    }

    // TODO: 19. This collects all known entries in Shared Preferences, with the help of the ID List
    // read all entries
    fun readAllEntries(): MutableList<JournalEntry> {
        // read list of entry ids
        val listOfIds = getListOfIds()

        // step through that list and read each entry
        val entryList = java.util.ArrayList<JournalEntry>()
        for (id in listOfIds) {
            readEntry(id.toInt())?.let {
                entryList.add(it)
            }
        }
        return entryList
    }


    // TODO: 20. This is another way to define a SharedPreferences item
    // In Activity, can simply use: prefs.bgColor (to get and set)
    var bgColor: Int
        get() = sharedPrefs.getInt(BACKGROUND_COLOR, Color.BLACK)
        set(value) = sharedPrefs.edit().putInt(BACKGROUND_COLOR, value).apply()


    // TODO: 21. Update an entry - use CSV technique to "serialize" a Journal Entry
    // edit an existing entry
    fun updateEntry(entry: JournalEntry) {
        val editor = sharedPrefs.edit()
        editor.putString(ENTRY_ITEM_KEY_PREFIX + entry.id, entry.toCsvString())
        editor.apply()
    }
}