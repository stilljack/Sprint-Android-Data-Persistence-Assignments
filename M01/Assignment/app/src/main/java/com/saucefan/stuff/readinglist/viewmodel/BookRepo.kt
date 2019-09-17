package com.saucefan.stuff.readinglist.viewmodel

import com.saucefan.stuff.readinglist.model.Book
import kotlin.random.Random

object BookRepo {

    //we'll set these two so we can track where to clean up books that have had their titles changed
    var titleChanged = ""
    var titleChangedBool = false


    //GOD HELP US IF WE LOSE TRACK
    //we will update idcount on save
    //this is not great
    var idCount = 0
   // var bookList = mutableListOf<Book>()

    fun getNewID():Int {
        return idCount++
    }


    var fakeBooks = mutableListOf<Book>(
        Book("variables1","none",false,-1),
        Book("variables2","less than none",true,-1),
        Book("variables3","less than that",false,-1),
        Book("variables4","i need to get back on zoloft",true,-1),
        Book("variables5","more stuff",false,-1),
        Book("variables6","some days",true,-1),
        Book("variables7","just less sunlight",false,-1),
        Book("variables8","or too much",true,-1),
        Book("variables9","too little",false,-1),
        Book("variables10","too much",true,-1)
        )

    fun randBook(): Book {
        var book=fakeBooks[Random.nextInt(0, fakeBooks.size)]
        book.id = getNewID()

       return book
    }
}