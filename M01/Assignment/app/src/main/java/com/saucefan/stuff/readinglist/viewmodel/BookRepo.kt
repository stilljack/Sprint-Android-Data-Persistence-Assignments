package com.saucefan.stuff.readinglist.viewmodel

import com.saucefan.stuff.readinglist.model.Book
import kotlin.random.Random

object BookRepo {

    var fakeBooks = mutableListOf<Book>(
        Book("variables1","none",false,"0"),
        Book("variables2","less than none",true,"1"),
        Book("variables3","less than that",false,"2"),
        Book("variables4","i need to get back on zoloft",true,"3"),
        Book("variables5","maybe just start microdosing again",false,"4"),
        Book("variables6","some days",true,"5"),
        Book("variables7","just less sunlight",false,"6"),
        Book("variables8","or too much",true,"7"),
        Book("variables9","too little",false,"8"),
        Book("variables10","too much",true,"9")
        )

    fun randBook(): Book {
       return fakeBooks[Random.nextInt(0, fakeBooks.size)]
    }
}