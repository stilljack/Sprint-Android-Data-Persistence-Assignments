package com.saucefan.stuff.readinglist.model

class book

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
        // check to see if we have the right string
        if (values.size == 4) {
            try {
                this.title = values[0]
                this.reasonToRead = values[1]
                this.hasBeenRead = values[2].toBoolean()
                this.id = id
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
        }
    }




}