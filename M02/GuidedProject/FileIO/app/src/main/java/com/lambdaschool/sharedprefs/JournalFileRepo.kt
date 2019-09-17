package com.lambdaschool.sharedprefs

import android.content.Context
import android.os.Environment
import com.lambdaschool.sharedprefs.model.JournalEntry
import java.io.*

class JournalFileRepo (var context: Context):JournalRepoInterface {
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
    override fun readAllEntries(): MutableList<JournalEntry> {
        //fileList
        val entries = MutableList<JournalEntry>(filelist.size,0)
        // arraylist
        //read in files
       //convert to objects

        for(filename in filelist) {
            val json = readFromFile(filename)
        }

    }


    private fun readFromFile (filename:String):String{
        val inputFile = File(storageDirectory,filename)
       // val readData = StringBuilder()
        var readString:String? = null
        var reader:FileReader? = null
        try {
            reader = FileReader(inputFile)
            readString =reader.readText()
           // var next =reader.read()
         /*   while (next !=-1)
            {
                readData.append(next)
                next=reader.read()
            }*/
        } catch (e:FileNotFoundException)
        {
            e.printStackTrace()
        }
        catch (e:IOException)
        {
            e.printStackTrace()
        }
        finally {
            if (reader!=null) {
                reader.close()
            }
        }
return readString.toString()

    }
    override fun updateEntry(entry: JournalEntry) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteEntry(entry: JournalEntry) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createEntry(entry:JournalEntry) {
        val entryString = entry.toJsonObject()
        val filename = entry.date +".json"
        writeToFile(filename, entryString.toString())
    }
    private fun writeToFile(filename: String, entryString: String) {
        val dir = storageDirectory
        val outputFile = File(dir, filename)
        var writer: FileWriter? = null
        try {
            writer = FileWriter(outputFile)
            writer.write(entryString)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (writer != null) {
                try {
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
                val directory=context.filesDir
              //  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    // brian sayz u this onez 4 productionz context.filesDir
                return if (!directory.exists() && !directory.mkdirs()) {
                    context.cacheDir
                } else {
                    directory
                }

            }
            else {
                return context.cacheDir
            }
            /* context.filesDir
             context.getExternalFilesDir("fire")
                 context.cacheDir*/
        }

         val isExternalStorageWriteable:Boolean
            get() {
                val state = Environment.getExternalStorageState()
                return state == Environment.MEDIA_MOUNTED
            }

}