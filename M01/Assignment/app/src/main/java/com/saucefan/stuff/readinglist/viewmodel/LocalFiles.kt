package com.saucefan.stuff.readinglist.viewmodel

import android.content.Context
import android.os.Environment
import com.google.gson.Gson
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.getNewID
import org.json.JSONException
import java.io.*




class LocalFiles(var context: Context) :StorageInterface {
    //check permissions
    val gson= Gson()
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

    private fun readFromFile (filename:String):String{
        val inputFile = File(storageDirectory,filename)
        var readString:String? = null
        var reader: FileReader? = null
        try {
            reader = FileReader(inputFile)
            readString =reader.readText()
        } catch (e: FileNotFoundException)
        {
            e.printStackTrace()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        finally {
            if (reader!=null) {
                reader.close()
            }
        }
        return readString ?: ""

    }
    private fun writeToFile(filename: String, entryString: String) {
        val dir = storageDirectory
        val outputFile = File(dir, filename)
        //heres the the guy doing all the work
        var writer: FileWriter? = null
        //this seems fine
        try {
            writer = FileWriter(outputFile)
            writer.write(entryString)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {

            //remember to close
            if (writer != null) {
                try {
                    writer.close()
                } catch (e2: IOException) {
                    e2.printStackTrace()
                }
            }
        }
    }
    override fun createEntry(book: Book) {
            val bookString=gson.toJson(book)
            val filename = book.title+".json"
            writeToFile(filename,bookString)
         }

    override fun readAllEntries(): MutableList<Book> {
        val books= mutableListOf<Book>()
        for (file in filelist) {
            val json = readFromFile(file)
            if (json!="") {
                try {
                    var theSauce = gson.fromJson(json,Book::class.java)
                    theSauce.id = getNewID()
                    books.add(theSauce)
                }catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        return books
        }

    override fun updateEntry(book: Book) {
        // should be just create
        createEntry(book)
             }

    override fun deleteEntry(book: Book) {
        val dir = storageDirectory
        val filename =book.title+".json"
        val file = File(dir,filename)
        if (file.exists()) {
            file.canonicalFile.delete()
            if (file.exists()) {
                context.deleteFile(file.name)
            }
        }
    }
}
