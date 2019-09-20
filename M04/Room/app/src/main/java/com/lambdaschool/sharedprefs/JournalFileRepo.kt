package com.lambdaschool.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambdaschool.sharedprefs.model.JournalEntry
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.StringBuilder

class JournalFileRepo(var context: Context): JournalRepoInterface {

    override fun updateEntry(entry: JournalEntry) {
        createEntry(entry)
    }

    override fun deleteEntry(entry: JournalEntry) {
        val filename = "journalEntry${entry.date}.json"
        if (filename.contains(filename)) {
            val deleteFile = File(storageDirectory, filename)
            try {
                deleteFile.delete()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // Basic structure: We will save each object to its own json file

    override fun createEntry(entry: JournalEntry) {
        val entryString = entry.toJsonObject()
        val filename = "journalEntry${entry.date}.json"
        writeToFile(filename, entryString.toString())
    }

    private fun writeToFile(filename: String, entryString: String) {
        val dir = storageDirectory
        val outputFile = File(dir, filename)

        // Open FileWriter
        var writer: FileWriter? = null
        try {
            // Write
            writer = FileWriter(outputFile)
            writer.write(entryString)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (writer != null) {
                try {
                    // Close
                    writer.close()
                } catch (e2: IOException) {
                    e2.printStackTrace()
                }
            }
        }
    }

    val storageDirectory: File
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

    val isExternalStorageWriteable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return state == Environment.MEDIA_MOUNTED
        }

    override fun readAllEntries(): LiveData<List<JournalEntry>> {
        // get filelist
        val entries = ArrayList<JournalEntry>()

        // setup ArrayList
        // read in files and convert to objects

        for (filename in filelist) {
            val json = readFromFile(filename)
            try {
                entries.add(JournalEntry(JSONObject(json)))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val liveData = MutableLiveData<List<JournalEntry>>()
        liveData.postValue(entries)
        return liveData
    }

    val filelist: ArrayList<String>
        get() {
            val fileNames = arrayListOf<String>()
            val dir = storageDirectory

            val list = dir.list()
            if (list != null) {
                for (name in list) {
                    if (name.contains(".json")) {
                        fileNames.add(name)
                    }
                }
            }
            return fileNames
        }

    private fun readFromFile(filename: String): String {
        val inputFile = File(storageDirectory, filename)
        var readString: String? = null
        var reader: FileReader? = null
        try {
            reader = FileReader(inputFile)
            readString = reader.readText()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return readString ?: ""
    }
}