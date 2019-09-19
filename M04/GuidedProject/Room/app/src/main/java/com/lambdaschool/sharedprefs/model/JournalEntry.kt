package com.lambdaschool.sharedprefs.model

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO 6: Annotate the Entity

class JournalEntry : Serializable {

    companion object {
        const val TAG = "JournalEntry"
        // TODO 8: This must be 0 if we want autoGenerate to work
        const val INVALID_ID = -1
    }

    var date: String? = null
    var entryText: String? = null
    var image: String? = null
    var dayRating: Int = 0

    // TODO 7: Let's make id the primary key
    var id: Int = 0

    constructor(id: Int) {
        this.id = id
        this.entryText = ""
        this.image = ""

        initializeDate()
    }

    constructor(jsonObject: JSONObject) {
        try {
            this.date = jsonObject.getString("date")
        } catch (e: JSONException) {
            this.date = (Date().time / 1000).toString()
        }
        try {
            this.entryText = jsonObject.getString("entry_text")
        } catch (e: JSONException) {
            this.entryText = ""
        }
        try {
            this.image = jsonObject.getString("image")
        } catch (e: JSONException) {
            this.image = ""
        }
        try {
            this.dayRating = jsonObject.getInt("day_rating")
        } catch (e: JSONException) {
            this.dayRating = 0
        }
        try {
            this.id = jsonObject.getInt("id")
        } catch (e: JSONException) {
            this.id = -1
        }
    }

    fun toJsonObject(): JSONObject? {
        try {
            return JSONObject().apply {
                put("date", date)
                put("entry_text", entryText)
                put("image", image)
                put("day_rating", dayRating)
                put("id", id)
            }
        } catch (e1: JSONException) {
            return try {
                JSONObject("{\"date\" : \"$date\", \"entry_text\" : \"$entryText\", \"image\": \"$image\", \"day_rating\": $dayRating, \"id\": $id}")
            } catch (e2: JSONException) {
                e2.printStackTrace()
                return null
            }
        }

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

        this.date = (date.time / 1000).toString()
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
