package com.saucefan.stuff.readinglist.model

import timber.log.Timber
import java.io.Serializable

class Book: Serializable

{

    var title: String? = null
    var reasonToRead: String? = null
    var hasBeenRead: Boolean? = null
    var id: String = ""

    constructor(
        title:String,
        reasonToRead:String,
        hasBeenRead:Boolean,
        id:String
    ) {
        this.title = title
        this.reasonToRead = reasonToRead
        this.hasBeenRead = hasBeenRead
        this.id = id
    }
    constructor(
        csvString: String
    ) {
        val values = csvString.split(",")
        // just making sure we at least have the correct number of things
        if (values.size == 4) {
                this.title = values[0]
            // unescape them
                this.reasonToRead = values[1].replace("~@",",")
                this.hasBeenRead = values[2].toBoolean()
                this.id = id
        }
        else {
            Timber.e("csv2obj error $this ${this.title} / ${this.reasonToRead} / ${this.hasBeenRead} / ${this.id}")
        }
    }
// these might come in handy
override fun toString(): String {
    return "JournalEntry(title=$title, reasonToRead=$reasonToRead, hasBeenRead=$hasBeenRead, id=$id)"
}
    companion object {
        const val TAG = "BookEntry"
        const val INVALID_ID = -1
    }
    fun toCsvString(): String {
        Timber.i("CSVtoSTRING: $title,${reasonToRead?.replace(",","~@")},$hasBeenRead,$id")
        if (title.isNullOrBlank()) {
            Timber.e("title is null or blank **$title**")
            this.title="no title"
        }
        if(reasonToRead.isNullOrBlank()){
            Timber.e("RtR is null or blank **$reasonToRead**")
            this.reasonToRead="no RtR"
        }
        if (id.isNullOrBlank()) {
            Timber.e("id is null or blank **$id**")
            this.id = "0"
        }
        return "$title,${reasonToRead?.replace(",","~@")},$hasBeenRead,$id"
    }


}


/*       // handle missing numbers or strings in the number position
       try {
           this.title = Integer.parseInt(values[0])
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
       this.image = if (values[4] == "unused") "" else values[4]*/