package com.lambdaschool.sharedprefs.model

import android.net.Uri
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: 22. The Model Class for a Journal Entry
class JournalEntry : Serializable {

    companion object {
        const val TAG = "JournalEntry"
        const val INVALID_ID = -1
    }

    var date: String? = null
    var entryText: String? = null
    private var image: String? = null
    var dayRating: Int = 0
    var id: Int = 0

    constructor(id: Int) {
        this.id = id
        this.entryText = ""
        this.image = ""

        initializeDate()
    }

    constructor(csvString: String) {
        val values = csvString.split(",")
        // check to see if we have the right string
        if (values.size == 5) {
            // handle missing numbers or strings in the number position
            try {
                this.id = Integer.parseInt(values[0])
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

            this.date = values[1]
            try {
                this.dayRating = Integer.parseInt(values[2])
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

            // allows us to replace commas in the entry text with a unique character and
            // preserve entry structure
            this.entryText = values[3].replace("~@", ",")
            // placeholder for image will maintain csv's structure even with no provided image
            this.image = if (values[4] == "unused") "" else values[4]
        }
    }

    // TODO: 23. One approach to save an object into a String
    // converting our object into a csv string that we can handle in a constructor
    internal fun toCsvString(): String {
        return String.format(
            "%d,%s,%d,%s,%s",
            id,
            date,
            dayRating,
            entryText?.replace(",", "~@"),
            if (image === "") "unused" else image
        )
    }

    private fun initializeDate() {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US)
        val date = Date()

        this.date = dateFormat.format(date)
    }

    fun getImage(): Uri? {
        return if (image != "") {
            Uri.parse(image)
        } else {
            null
        }
    }

    fun setImage(imageUri: Uri) {
        this.image = imageUri.toString()
    }

    override fun toString(): String {
        return "JournalEntry(date=$date, entryText=$entryText, image=$image, dayRating=$dayRating, id=$id)"
    }
}
