package com.saucefan.stuff.readinglist.view

import android.R.attr.fragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.saucefan.stuff.readinglist.R
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.idCount
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.randBook
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookview.view.*


class MainActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(book: Book) {

    }

    var itemsInList = 0


    fun buildIemView(book: Book): View {
        /*    book.title
            book.id
            book.reasonToRead
            book.hasBeenRead*/
        book.id= ll.childCount.toString()
        var item = layoutInflater.inflate(R.layout.bookview, null, false)
        item.tag=book
        item.title.text="${book.title}"
        item.reasonToRead.text="${book.reasonToRead}"
        if (book.hasBeenRead == true) {
            item.background= getDrawable(R.color.bgHightlight)
            item.checkBox.setChecked(true)
            item.checkBox.invalidate()
        } else item.background = getDrawable(R.color.bgRegular)
        item.setOnClickListener {
            //intent stuff
            openFragForBook(book, item)
        }
        return item



    }

    private fun openFragForBook(
        book: Book,
        item: View
    ) {
        val frag = EditFragment.newInstance(book.title.toString(), book)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        val bundle = Bundle()
        bundle.putSerializable(EDIT_BOOK, book)
        frag.arguments = bundle
        transaction.add(frag, "Edit Fragment ${item.tag}")
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_whatever.setOnClickListener {
            ll.addView(buildIemView(randBook()))
        }

        btn_newitem.setOnClickListener {
            //making a new item is the same thing as editing an old item with default prompts, hence, to make a new item we're just going
            // to open up the same ol' edit fragment with a new book object
            openFragForBook(Book("Enter title","Enter Reason to Read",false,ll.childCount.toString()),btn_newitem)
        }


    }
}
