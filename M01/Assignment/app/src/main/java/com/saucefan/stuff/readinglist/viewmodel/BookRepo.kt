package com.saucefan.stuff.readinglist.viewmodel

import com.saucefan.stuff.readinglist.model.Book
import kotlin.random.Random

object BookRepo {

    //GOD HELP US IF WE LOSE TRACK
    //we will update idcount on save
    var idCount = 0
    var bookList = mutableListOf<Book>()

    fun getNewID():String {
        var id = idCount
        idCount++
        return id.toString()



    }













    var fakeBooks = mutableListOf<Book>(
        Book("variables1","none",false,"0"),
        Book("variables2","less than none",true,"1"),
        Book("variables3","less than that",false,"2"),
        Book("variables4","i need to get back on zoloft",true,"3"),
        Book("variables5","more stuff",false,"4"),
        Book("variables6","some days",true,"5"),
        Book("variables7","just less sunlight",false,"6"),
        Book("variables8","or too much",true,"7"),
        Book("variables9","too little",false,"8"),
        Book("variables10","too much",true,"9")
        )

    fun randBook(): Book {
        var book=fakeBooks[Random.nextInt(0, fakeBooks.size)]
       return book
    }
}