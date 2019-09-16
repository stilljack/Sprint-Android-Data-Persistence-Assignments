package com.saucefan.stuff.readinglist.view

import android.R.attr.fragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.saucefan.stuff.readinglist.R
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.idCount
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.randBook
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookview.view.*
import kotlinx.android.synthetic.main.fragment_edit.view.*


class MainActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener {
    override fun onFragSave(book: Book) {
        var found = false
        for (view in ll.children) {
            if (view.tag == book.id) {
                found=true
                view.title.text = book.title
                view.reasonToRead.text = book.reasonToRead
                view.tv_id_list.text = book.id
                view.setOnClickListener {
                    //intent stuff
                    openFragForBook(book, view)
                }
            }
        }
        if (found==false) {
            ll.addView(buildIemView(book))
        }

    }

    var itemsInList = 0


    fun buildIemView(book: Book): View {
        /*    book.title
            book.id
            book.reasonToRead
            book.hasBeenRead*/

        book.id = ll.childCount.toString()
        var item = layoutInflater.inflate(R.layout.bookview, null, false)
        item.tv_id_list.text = book.id
        item.tag = book.id
        item.title.text = "${book.title}"
        item.reasonToRead.text = "${book.reasonToRead}"
        if (book.hasBeenRead == true) {
            item.background = getDrawable(R.color.bgHightlight)
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
            ll.addView(buildIemView(randBook(ll.childCount.toString())))
        }

        btn_newitem.setOnClickListener {
            //making a new item is the same thing as editing an old item with default prompts, hence, to make a new item we're just going
            // to open up the same ol' edit fragment with a new book object
            openFragForBook(Book("Enter title","Enter Reason to Read",false,ll.childCount.toString()),btn_newitem)
        }


    }
}
